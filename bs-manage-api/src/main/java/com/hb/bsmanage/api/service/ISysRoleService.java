package com.hb.bsmanage.api.service;

import com.hb.bsmanage.model.po.SysRolePO;
import com.hb.mybatis.base.IDmlMapper;

import java.util.List;
import java.util.Set;

/**
 * 角色service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysRoleService extends IDmlMapper<SysRolePO, Integer, String> {

    /**
     * 通过角色ID集合查询角色信息集合
     *
     * @param roleIdSet 角色ID集合
     * @return 角色信息集合
     */
    List<SysRolePO> getRoleListByRoleIdSet(Set<String> roleIdSet);

}
