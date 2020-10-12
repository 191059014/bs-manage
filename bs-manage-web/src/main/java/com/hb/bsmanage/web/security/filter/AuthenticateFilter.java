package com.hb.bsmanage.web.security.filter;

import com.hb.bsmanage.web.common.RedisKeyFactory;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.bsmanage.web.common.constans.Consts;
import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.bsmanage.web.security.config.SecurityProperties;
import com.hb.bsmanage.web.security.model.RbacContext;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.unic.base.GlobalProperties;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.base.util.ServletUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.JsonUtils;
import com.hb.unic.util.util.StrUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
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
@Configuration
public class AuthenticateFilter extends OncePerRequestFilter {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateFilter.class);

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
        String baseLog = LogHelper.getBaseLog("token认证");
        if (checkIgnores(request)) {
            LOGGER.info("{}直接放行[{}]", baseLog, request.getRequestURI());
            chain.doFilter(request, response);
            return;
        }
        try {
            /*
             * 校验token
             */
            String token = request.getHeader(Consts.TOKEN);
            LOGGER.info("{}token={}", baseLog, token);
            if (StrUtils.isBlank(token)) {
                ServletUtils.writeResponse(response, JsonUtils.toJson(Result.of(ErrorCode.TOKEN_IS_EMPTY)));
                return;
            }
            /*
             * 从缓存里获取rbac信息
             */
            String tokenKey = RedisKeyFactory.getTokenKey(token);
            String json = ToolsWapper.redis().get(tokenKey);
            if (json == null) {
                ServletUtils.writeResponse(response, JsonUtils.toJson(Result.of(ErrorCode.TOKEN_IS_EXPIRED)));
                return;
            }
            RbacContext rbacContext = JsonUtils.toBean(json, RbacContext.class);
            if (rbacContext == null) {
                ServletUtils.writeResponse(response, JsonUtils.toJson(Result.of(ErrorCode.TOKEN_IS_EXPIRED)));
                return;
            }
            /*
             * 将rbac信息放入上下文
             */
            SecurityUtils.setRbacContext(rbacContext);
            /*
             * 给token过期时间续航
             */
            ToolsWapper.redis().setExpire(tokenKey, GlobalProperties.getLong("token.defaultTtl"));

            LOGGER.info("{}完毕，放行", baseLog);
            //放行
            chain.doFilter(request, response);
        } finally {
            SecurityUtils.clearRbacContext();
        }
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

    