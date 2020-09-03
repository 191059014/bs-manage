package com.hb.bsmanage.api.sys.impl;

import com.hb.bsmanage.api.sys.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import org.springframework.stereotype.Service;

/**
 * 用户service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserServiceImpl extends DmlMapperImpl<SysUserDO> implements ISysUserService {

    @Override
    public SysUserDO findByUserIdOrMobile(String userIdOrMobile) {
        Where where = Where.build().addSingle(QueryType.EQUAL, "user_id", userIdOrMobile)
                .or()
                .addSingle(QueryType.EQUAL, "mobile", userIdOrMobile);
        return selectOne(where);
    }

}

    