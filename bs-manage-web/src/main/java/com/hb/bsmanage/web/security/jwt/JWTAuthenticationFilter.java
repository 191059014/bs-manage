package com.hb.bsmanage.web.security.jwt;

import com.hb.bsmanage.web.security.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/**
 * token认证
 *
 * @author Mr.Huang
 * @version v0.1, JWTAuthenticationFilter.java, 2020/6/18 13:23, create by huangbiao.
 */
@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsService userDetailsService;

    /**
     * security配置
     */
    @Autowired
    private SecurityProperties securityProperties;

    /**
     * 在此方法中检验客户端请求头中的token,
     * 如果存在并合法,就把token中的信息封装到 Authentication 类型的对象中,
     * 最后使用  SecurityContextHolder.getContext().setAuthentication(authentication); 改变或删除当前已经验证的 pricipal
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("JWTAuthenticationFilter.doFilterInternal: " + request.getRequestURI());
        if (checkIgnores(request)) {
            System.out.println("不需要进行权限拦截");
            //放行
            chain.doFilter(request, response);
            return;
        }
        String jwt = jwtUtils.getJwtFromRequest(request);
        String username = jwtUtils.getUsernameFromJWT(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //放行
        chain.doFilter(request, response);
    }

    /**
     * 请求是否不需要进行权限拦截
     *
     * @param request 当前请求
     * @return true-忽略，false-不忽略
     */
    private boolean checkIgnores(HttpServletRequest request) {
        Set<String> ignoreUrlPatterns = securityProperties.getIgnoreUrlPatterns();
        if (!CollectionUtils.isEmpty(ignoreUrlPatterns)) {
            for (String ignoreUrl : ignoreUrlPatterns) {
                AntPathRequestMatcher matcher = new AntPathRequestMatcher(ignoreUrl);
                if (matcher.matches(request)) {
                    return true;
                }
            }
        }
        return false;
    }

}

    