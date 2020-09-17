package com.hb.bsmanage.web.security.model;

import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
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
    private String userName;

    // 密码
    private String password;

    // 权限信息
    private Collection<? extends GrantedAuthority> authorities;

    public UserPrincipal(String userName, String password, List<String> permissions) {
        this.userName = userName;
        this.password = password;
        if (CollectionUtils.isNotEmpty(permissions)) {
            this.authorities = permissions.stream()
                    .map(permission -> new SimpleGrantedAuthority(permission))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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

    