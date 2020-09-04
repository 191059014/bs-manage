package com.hb.bsmanage.web.security.jwt;

import com.hb.bsmanage.model.common.Consts;
import com.hb.bsmanage.web.common.RedisKeyFactory;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.unic.base.GlobalProperties;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * JWT工具类
 *
 * @author Mr.Huang
 * @version v0.1, JwtUtils.java, 2020/6/17 15:32, create by huangbiao.
 */
public class JwtUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

    /**
     * 角色
     */
    private static final String ROLES = "roles";

    /**
     * 权限
     */
    private static final String AUTHORITIES = "authorities";

    /**
     * 密钥
     */
    private static String getKey() {
        return GlobalProperties.getString("jwt.config.key");
    }

    /**
     * 默认过期时间
     */
    private static long getDefaultTtl() {
        return GlobalProperties.getLong("jwt.config.defaultTtl");
    }

    /**
     * 记住我，过期时间
     */
    private static long getRememberMeTtl() {
        return GlobalProperties.getLong("jwt.config.rememberMeTtl");
    }

    /**
     * 生成jwt（也可以和redis一起使用，将token放到redis里面）
     *
     * @param userId      用户表主键
     * @param username    用户标识
     * @param roles       角色
     * @param authorities 权限
     * @return jwt令牌
     */
    public static String createToken(String userId, String username, List<String> roles, Collection<? extends GrantedAuthority> authorities, boolean rememberMe) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(username)) {
            return null;
        }
        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置用户标识
                .setId(userId)
                // jwt所面向的用户
                .setSubject(username)
                // 设置角色
                .claim(ROLES, roles)
                // 设置权限
                .claim(AUTHORITIES, authorities)
                // 设置签发日期
                .setIssuedAt(new Date())
                // 签名方式
                .signWith(SignatureAlgorithm.HS256, getKey());

        long expireTime = rememberMe ? getRememberMeTtl() : getDefaultTtl();
        // 设置过期日期
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expireTime));
        String token = jwtBuilder.compact();
        // 将token放进redis缓存
        String jwtKey = RedisKeyFactory.getJwtKey(username);
        LOGGER.info("将token放进redis缓存={}", jwtKey);
        ToolsWapper.redis().set(jwtKey, token, expireTime);

        return token;
    }


    /**
     * 校验token（也可以和redis一起使用，看redis里是否包含token缓存）
     *
     * @param token jwt令牌
     * @return Claims
     */
    public static Claims parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResponseEnum.NULL_TOKEN);
        }
        String simpleToken = token.replace(Consts.TOKEN_BEARER, "");
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(simpleToken)
                .getBody();

        // 从redis缓存中拿到token，验证token合法性
        String jwtKey = RedisKeyFactory.getJwtKey(claims.getSubject());
        String redisToken = ToolsWapper.redis().getString(jwtKey);
        if (redisToken == null) {
            throw new BusinessException(ResponseEnum.EXPIRE_TOKEN);
        }
        if (!simpleToken.equals(redisToken)) {
            throw new BusinessException(ResponseEnum.ILLEGAL_TOKEN);
        }
        return claims;
    }

    /**
     * 从request里面获取jwt令牌
     *
     * @param request 请求
     * @return jwt令牌
     */
    public static String getJwtFromRequest(HttpServletRequest request) {
        return request.getHeader(Consts.TOKEN);
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public static String getUsernameFromJWT(String jwt) {
        Claims claims = parseToken(jwt);
        return claims == null ? null : claims.getSubject();
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public static void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        ToolsWapper.redis().delete(RedisKeyFactory.getJwtKey(username));
    }

}

    