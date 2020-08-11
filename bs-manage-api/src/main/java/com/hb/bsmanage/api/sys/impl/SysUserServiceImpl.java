package com.hb.bsmanage.api.sys.impl;

import com.hb.bsmanage.api.sys.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.mybatis.base.DmlMapper;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.sql.Query;
import org.springframework.stereotype.Service;

/**
 * 用户service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserServiceImpl extends DmlMapper<Integer, SysUserDO> implements ISysUserService {

    @Override
    public SysUserDO findOne(SysUserDO sysUserDO) {
        return selectOne(Query.build().add(sysUserDO));
    }

    @Override
    public int add(SysUserDO sysUserDO) {
        return insertBySelective(sysUserDO);
    }

    @Override
    public SysUserDO findByUserIdOrMobile(String userIdOrMobile) {
        Query query = Query.build()
                .add(QueryType.EQUAL, "user_id", userIdOrMobile)
                .sql(" or ")
                .add(QueryType.EQUAL, "mobile", userIdOrMobile);
        return selectOne(query);
    }

    @Override
    public SysUserDO findById(Integer id) {
        return selectById(id);
    }

}

    