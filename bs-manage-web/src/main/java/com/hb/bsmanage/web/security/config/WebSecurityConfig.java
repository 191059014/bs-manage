package com.hb.bsmanage.web.security.config;

import com.hb.bsmanage.web.security.jwt.JWTAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.CollectionUtils;

import java.util.Set;

/**
 * SpringSecurity配置
 *
 * @author Mr.Huang
 * @version v0.1, WebSecurityConfig.java, 2020/6/1 14:40, create by huangbiao.
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * userDetailsService
     */
    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * jwt认证过滤器
     */
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;

    /**
     * security配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 密码加密器
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 密码加密
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors()
                // 关闭 CSRF
                .and().csrf().disable()
                // 登录行为由自己实现，参考 com.hb.test.springsecurity.jwt.controller.LoginController.doLogin
                .formLogin().disable()
                // 关闭http验证
                .httpBasic().disable()
                // 认证请求
                .authorizeRequests()
                // 所有请求都需要登录访问
//                .anyRequest()
//                .authenticated()
                // RBAC 动态 url 认证
                .anyRequest()
                .access("@rbacAuthorityService.hasPermission(authentication)")
                // 登出行为由自己实现，参考 com.hb.test.springsecurity.jwt.controller.LoginController.logout
                .and().logout().disable()
                // Session 管理
                .sessionManagement()
                // 因为使用了JWT，所以这里不管理Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 异常处理
                .and().exceptionHandling().accessDeniedHandler((request, response, e) -> {
            System.out.println("权限不足");
            response.sendRedirect("/403");
        });

        // 添加自定义 JWT 过滤器
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 忽略请求
        WebSecurity ws = web.ignoring().and();
        // 按照请求url忽略
        Set<String> ignoreUrlPatterns = securityProperties.getIgnoreUrlPatterns();
        if (!CollectionUtils.isEmpty(ignoreUrlPatterns)) {
            ignoreUrlPatterns.forEach(url -> ws.ignoring().antMatchers(url));
        }
    }
}

    