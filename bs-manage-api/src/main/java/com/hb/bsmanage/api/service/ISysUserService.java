package com.hb.bsmanage.api.service;

import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.mybatis.base.IDmlMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysUserService extends IDmlMapper<SysUserDO, Integer, String> {

    /**
     * 通过用户ID或者手机号查询用户
     *
     * @param usernameOrMobile 用户ID或者手机号查询
     * @return 用户信息
     */
    SysUserDO findByUsernameOrMobile(String usernameOrMobile);

    /**
     * 通过用户ID集合查询用户信息
     *
     * @param userIdSet 用户ID集合
     * @return 用户信息集合
     */
    List<SysUserDO> getUserListByUserIdSet(Set<String> userIdSet);

    /**
     * 通过用户ID集合查询用户信息
     *
     * @param userIdSet 用户ID集合
     * @return 用户信息集合
     */
    Map<String, SysUserDO> getUserMapByUserIdSet(Set<String> userIdSet);

}
