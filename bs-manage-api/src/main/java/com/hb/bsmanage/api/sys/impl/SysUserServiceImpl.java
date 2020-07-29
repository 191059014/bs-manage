package com.hb.bsmanage.api.sys.impl;

import com.hb.bsmanage.api.sys.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.mybatis.base.SimpleMybatisService;
import com.hb.mybatis.sql.Query;
import org.springframework.stereotype.Service;

/**
 * 用户service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserServiceImpl extends SimpleMybatisService implements ISysUserService {

    @Override
    public SysUserDO findOne(SysUserDO sysUserDO) {
        return selectOne(SysUserDO.class, Query.build().analysisAll(sysUserDO));
    }

    @Override
    public int add(SysUserDO sysUserDO) {
        return insertBySelective(sysUserDO);
    }

}

    