package com.hb.bsmanage.web.service;

import com.hb.bsmanage.web.dao.base.IBaseDao;
import com.hb.bsmanage.web.dao.po.SysUserRolePO;

import java.util.Set;

/**
 * 用户角色关系service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysUserRoleService extends IBaseDao<SysUserRolePO> {

    /**
     * 通过用户ID查询角色ID集合
     *
     * @param userId
     *            用户id
     * @return 角色id集合
     */
    Set<String> getRoleIdSetByUserId(String userId);

}
