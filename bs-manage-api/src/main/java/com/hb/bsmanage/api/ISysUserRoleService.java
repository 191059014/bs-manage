package com.hb.bsmanage.api;

import com.hb.bsmanage.model.dobj.SysUserRoleDO;
import com.hb.mybatis.base.IDmlMapper;

import java.util.Set;

/**
 * 用户角色关系service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysUserRoleService extends IDmlMapper<SysUserRoleDO, Integer, String> {

    /**
     * 通过用户ID查询角色ID集合
     *
     * @param userId 用户id
     * @return 角色id集合
     */
    Set<String> getRoleIdSetByUserId(String userId);

}
