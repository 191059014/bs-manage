package com.hb.bsmanage.web.security.model;

import com.hb.bsmanage.model.dobj.SysUserDO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

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
    private Set<String> roles;

    // 权限信息
    private Set<String> permissions;

}

    