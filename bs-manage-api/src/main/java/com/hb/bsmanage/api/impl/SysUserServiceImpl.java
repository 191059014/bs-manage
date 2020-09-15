package com.hb.bsmanage.api.impl;

import com.hb.bsmanage.api.ISysUserService;
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
public class SysUserServiceImpl extends DmlMapperImpl<SysUserDO, Integer, String> implements ISysUserService {

    @Override
    public SysUserDO findByUsernameOrMobile(String usernameOrMobile) {
        Where where = Where.build()
                .leftBracket()
                .add(QueryType.EQUAL, "user_name", usernameOrMobile)
                .or()
                .add(QueryType.EQUAL, "mobile", usernameOrMobile)
                .rightBracket();
        return selectOne(where);
    }

}

    