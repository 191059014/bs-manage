package com.hb.bsmanage.web.service;

import com.hb.bsmanage.web.dao.base.IBaseDao;
import com.hb.bsmanage.web.dao.po.SysRolePermissionPO;

import java.util.Set;

/**
 * 角色权限关系service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysRoleAccessService extends IBaseDao<SysRolePermissionPO> {

    /**
     * 通过角色ID集合查询角色权限关系集合
     *
     * @param roleIdSet
     *            角色ID集合
     * @return 角色权限关系集合
     */
    Set<String> getPermissionIdSetByRoleIdSet(Set<String> roleIdSet);

}
