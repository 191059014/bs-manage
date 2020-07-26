package com.hb.bsmanage.api.sys;

import com.hb.bsmanage.model.dobj.SysUserDO;

/**
 * 用户service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysUserService {

    /**
     * 查询用户信息
     *
     * @param sysUserDO 用户信息
     * @return 影响的条数
     */
    SysUserDO findOne(SysUserDO sysUserDO);

    /**
     * 新增用户信息
     *
     * @param sysUserDO 用户信息
     * @return 影响的条数
     */
    int add(SysUserDO sysUserDO);

}
