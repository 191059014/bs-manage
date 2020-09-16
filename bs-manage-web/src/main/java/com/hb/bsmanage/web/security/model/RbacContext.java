package com.hb.bsmanage.web.security.model;

import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * jwt包含的rbac信息
 *
 * @version v0.1, 2020/9/16 15:26, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacContext {

    // 用户信息
    private SysUserDO user;

    // 角色信息
    private List<SysRoleDO> roles;

    // 权限信息
    private List<SysPermissionDO> permissions;

}

    