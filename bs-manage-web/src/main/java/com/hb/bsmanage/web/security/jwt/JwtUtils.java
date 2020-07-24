package com.hb.bsmanage.web.security.jwt;

import com.hb.bsmanage.web.common.RedisKeyFactory;
import com.hb.bsmanage.web.common.ToolsWapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
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
@Component
public class JwtUtils {

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
    @Value("${jwt.config.key}")
    private String key;

    /**
     * 默认过期时间
     */
    @Value("${jwt.config.defaultTtl}")
    private long defaultTtl;

    /**
     * 记住我，过期时间
     */
    @Value("${jwt.config.rememberMeTtl}")
    private long rememberMeTtl;

    /**
     * 生成jwt（也可以和redis一起使用，将token放到redis里面）
     *
     * @param userId 用户标识
     * @return jwt令牌
     */
    public String createToken(String userId, String userName, List<String> roles, Collection<? extends GrantedAuthority> authorities, boolean rememberMe) {
        if (StringUtils.isEmpty(userId)) {
            return null;
        }

        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置用户标识
                .setId(userId)
                // jwt所面向的用户
                .setSubject(userName)
                // 设置角色
                .claim(ROLES, roles)
                // 设置权限
                .claim(AUTHORITIES, authorities)
                // 设置签发日期
                .setIssuedAt(new Date())
                // 签名方式
                .signWith(SignatureAlgorithm.HS256, key);

        long expireTime = rememberMe ? rememberMeTtl : defaultTtl;
        // 设置过期日期
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expireTime));
        String token = jwtBuilder.compact();
        // 将token放进redis缓存
        String jwtKey = RedisKeyFactory.getJwtKey(userName);
        ToolsWapper.redis().set(jwtKey, token, expireTime);

        return token;
    }


    /**
     * 校验token（也可以和redis一起使用，看redis里是否包含token缓存）
     *
     * @param token jwt令牌
     * @return Claims
     */
    public Claims parseToken(String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token.replace("Bearer ", ""))
                    .getBody();

            // 从redis缓存中拿到token，验证token合法性
            String jwtKey = RedisKeyFactory.getJwtKey(claims.getSubject());
            String redisToken = ToolsWapper.redis().getString(jwtKey);
            if (redisToken == null || !token.equals(redisToken)) {
                System.out.println("token无效");
                return null;
            }
            return claims;
        } catch (Exception e) {
            System.out.println("解析Token异常：" + e.getMessage());
            return null;
        }

    }

    /**
     * 从request里面获取jwt令牌
     *
     * @param request 请求
     * @return jwt令牌
     */
    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("token");
        if (!StringUtils.isEmpty(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 根据 jwt 获取用户名
     *
     * @param jwt JWT
     * @return 用户名
     */
    public String getUsernameFromJWT(String jwt) {
        Claims claims = parseToken(jwt);
        return claims.getSubject();
    }

    /**
     * 设置JWT过期
     *
     * @param request 请求
     */
    public void invalidateJWT(HttpServletRequest request) {
        String jwt = getJwtFromRequest(request);
        String username = getUsernameFromJWT(jwt);
        // 从redis中清除JWT
        ToolsWapper.redis().delete(RedisKeyFactory.getJwtKey(username));
    }

}

    