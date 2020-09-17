package com.hb.bsmanage.web.security.jwt;

import com.hb.bsmanage.model.common.Consts;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.security.config.SecurityProperties;
import com.hb.bsmanage.web.security.model.RbacContext;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.base.util.ServletUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.logger.util.LogExceptionWapper;
import com.hb.unic.util.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
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
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    /**
     * 用户service
     */
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
        String baseLog = "[JwtAuthenticationFilter-doFilterInternal-jwt认证过滤器]";
        if ("options".equals(request.getMethod())) {
            chain.doFilter(request, response);
            return;
        }
        if (checkIgnores(request)) {
            LOGGER.info("{}无需拦截[{}]，放行", baseLog, request.getRequestURI());
            //放行
            chain.doFilter(request, response);
            return;
        }
        try {
            // 解析jwt
            RbacContext rbacContext = JwtUtils.parseJwtToken(request.getHeader(Consts.TOKEN));
            // 将rbac信息放入上下文
            SecurityUtils.setRbacContext(rbacContext);
        } catch (BusinessException e) {
            LOGGER.info("{}业务异常={}", baseLog, LogExceptionWapper.getStackTrace(e));
            ServletUtils.writeResponse(response, JsonUtils.toJson(Result.of(e.getKey(), e.getMessage())));
            return;
        } catch (Exception e) {
            LOGGER.info("{}系统异常={}", baseLog, LogExceptionWapper.getStackTrace(e));
            ServletUtils.writeResponse(response, JsonUtils.toJson(Result.of(ResponseEnum.FAIL.getCode(), "token认证失败")));
            return;
        }
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

    