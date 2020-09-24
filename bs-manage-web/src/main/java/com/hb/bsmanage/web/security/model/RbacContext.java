package com.hb.bsmanage.web.security.model;

import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * rbac上下文
 *
 * @version v0.1, 2020/9/16 15:26, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RbacContext implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -1672746006031876865L;

    // 用户信息
    private SysUserPO user;

    // 角色信息
    private List<SysRolePO> roles;

    // 权限信息
    private List<SysPermissionPO> permissions;

}

    