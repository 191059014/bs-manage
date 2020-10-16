package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.common.util.BsWebUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * 自定义用户认证处理器
 *
 * @version v0.1, 2020/10/16 10:34, create by huangbiao.
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

    /**
     * 自定义用户信息service
     */
    @Resource(name = "customUserDetailsService")
    private UserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 获取表单输入中返回的用户名
        String userName = (String) authentication.getPrincipal();
        // 获取表单中输入的密码
        String password = (String) authentication.getCredentials();

        // 查询用户
        UserDetails userInfo = customUserDetailsService.loadUserByUsername(userName);
        if (userInfo == null) {
            throw new BadCredentialsException("用户名不存在");
        }

        // 判断密码是否正确
        if (!BsWebUtils.bCryptMatches(password, userInfo.getPassword())) {
            throw new BadCredentialsException("密码不正确");
        }

        // 这里还可以加一些其他信息的判断，比如用户账号已停用等判断...

        Collection<? extends GrantedAuthority> authorities = userInfo.getAuthorities();

        // 构建返回的用户登录成功的token
        return new UsernamePasswordAuthenticationToken(userInfo, password, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }

}

    