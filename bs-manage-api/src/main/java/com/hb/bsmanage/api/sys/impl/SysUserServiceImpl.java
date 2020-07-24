package com.hb.bsmanage.api.sys.impl;

import com.hb.bsmanage.api.base.AbstractBaseService;
import com.hb.bsmanage.api.sys.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import org.springframework.stereotype.Service;

/**
 * 用户service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserServiceImpl extends AbstractBaseService implements ISysUserService {

    @Override
    public int add(SysUserDO sysUserDO) {
        return insertBySelective(sysUserDO);
    }

}

    