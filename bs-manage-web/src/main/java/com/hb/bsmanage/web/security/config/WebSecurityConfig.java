package com.hb.bsmanage.web.security.config;

import com.hb.bsmanage.web.security.service.CustomAccessDeniedHandler;
import com.hb.bsmanage.web.security.service.CustomLogoutSuccessHandler;
import com.hb.bsmanage.web.security.service.LoginFailureHandler;
import com.hb.bsmanage.web.security.service.LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


//    @Autowired
//    private AuthenticateFilter authenticateFilter;

    /**
     * security配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 自定义认证处理器
     */
    @Autowired
    private AuthenticationProvider customAuthenticationProvider;

    /**
     * 未登录时处理器
     */
    @Autowired
    private AuthenticationEntryPoint customAuthenticationEntryPoint;

    /**
     * 登录成功处理器
     */
    @Autowired
    private AuthenticationSuccessHandler loginSuccessHandler;

    /**
     * 配置认证处理器
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // 跨域配置
                .cors().and()
                // 关闭 CSRF
                .csrf().disable()
                // 关闭http验证
                .httpBasic().disable();

        http
                // 表单登陆模式
                .formLogin()
                .loginPage("/static/views/login.html")
                // 指定登陆url
//                .loginProcessingUrl("/login")
                // 允许所有用户
                .permitAll()
                // 登陆成功处理器
                .successHandler(new LoginSuccessHandler())
                // 登陆失败处理器
                .failureHandler(new LoginFailureHandler());

        http
                // 对请求授权
                .authorizeRequests()
                // 忽略静态资源
                .antMatchers("/static/**", "/favicon.ico").permitAll()
                // 其他的需要登陆后才能访问
                .anyRequest().authenticated();

        http
                // 登出行为由自己实现
                .logout()
                // 指定登陆url
                .logoutUrl("/logout")
                // 允许所有用户
                .permitAll()
                // 登出成功处理器
                .logoutSuccessHandler(new CustomLogoutSuccessHandler());

        http
                // Session 管理
                .sessionManagement()
                // 不管理Session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http
                // 异常处理
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                // 权限不足异常处理
                .accessDeniedHandler(new CustomAccessDeniedHandler());

        // 添加自定义 JWT 过滤器
//        http.addFilterBefore(authenticateFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Override
    public void configure(WebSecurity web) {
        // 忽略请求
        WebSecurity ws = web.ignoring().and();
        // 按照请求url忽略
        Set<String> ignoreUrlPatterns = securityProperties.getIgnoreUrlPatterns();
        if (!CollectionUtils.isEmpty(ignoreUrlPatterns)) {
            ignoreUrlPatterns.forEach(url -> ws.ignoring().antMatchers(url));
        }
    }
}

    