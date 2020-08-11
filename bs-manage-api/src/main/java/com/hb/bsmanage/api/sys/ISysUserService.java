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

    /**
     * 通过用户ID或者手机号查询
     *
     * @param userIdOrMobile 用户ID或者手机号查询
     * @return 用户信息
     */
    SysUserDO findByUserIdOrMobile(String userIdOrMobile);

    /**
     * 通过id查询
     *
     * @param id id
     * @return 用户信息
     */
    SysUserDO findById(Integer id);
}
