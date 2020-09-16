package com.hb.bsmanage.web.security.model;

import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户整体模型
 *
 * @version v0.1, 2020/8/3 15:14, create by huangbiao.
 */
@Getter
public class UserPrincipal implements UserDetails {

    // 序列化ID
    private static final long serialVersionUID = -7620486743149052764L;

    // 用户信息
    private SysUserDO user;

    // 角色信息
    private List<SysRoleDO> roles;

    // 权限信息
    private List<SysPermissionDO> permissions;

    // 权限信息
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(SysUserDO sysUserDO, List<SysRoleDO> roles, List<SysPermissionDO> permissions) {
        this.user = sysUserDO;
        this.roles = roles;
        this.permissions = permissions;
        if (permissions != null && !permissions.isEmpty()) {
            this.authorities = permissions.stream()
                    .filter(sysPermission -> StringUtils.isNotBlank(sysPermission.getValue()))
                    .map(sysPermission -> new SimpleGrantedAuthority(sysPermission.getValue()))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

    