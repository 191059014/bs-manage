package com.hb.bsmanage.web.security.jwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hb.bsmanage.model.common.Consts;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.web.common.RedisKeyFactory;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.bsmanage.web.security.model.RbacContext;
import com.hb.unic.base.GlobalProperties;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.CloneUtils;
import com.hb.unic.util.util.JsonUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
     * 用户
     */
    private static final String USER = "user";

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
     * @param user        用户信息
     * @param roles       角色列表
     * @param permissions 权限列表
     * @return jwt令牌
     */
    public static String createToken(SysUserDO user, List<SysRoleDO> roles, List<SysPermissionDO> permissions, boolean rememberMe) {
        if (user == null) {
            return null;
        }
        JwtBuilder jwtBuilder = Jwts.builder()
                // 设置jwt的id,防止jwt被重新发送
                .setId(UUID.randomUUID().toString())
                // jwt所面向的用户
                .setSubject(user.getUserName())
                // 设置用户
                .claim(USER, user)
                // 设置角色
                .claim(ROLES, roles)
                // 设置权限
                .claim(AUTHORITIES, permissions)
                // 设置签发日期
                .setIssuedAt(new Date())
                // 签名方式
                .signWith(SignatureAlgorithm.HS256, getKey());

        long expireTime = rememberMe ? getRememberMeTtl() : getDefaultTtl();
        // 设置过期日期
        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + expireTime));
        String token = jwtBuilder.compact();
        // 将token放进redis缓存
        String jwtKey = RedisKeyFactory.getJwtKey(user.getUserId());
        LOGGER.info("将token放进redis缓存={}", jwtKey);
        ToolsWapper.redis().set(jwtKey, token, expireTime);

        return token;
    }


    /**
     * 校验token（也可以和redis一起使用，看redis里是否包含token缓存）
     *
     * @param token jwt令牌
     * @return JwtInfo
     */
    public static RbacContext parseJwtToken(String token) {
        // 校验token是否为空
        if (StringUtils.isEmpty(token)) {
            throw new BusinessException(ResponseEnum.NULL_TOKEN);
        }
        // 校验token是否以Bearer 开头
        if (!token.startsWith(Consts.TOKEN_BEARER)) {
            throw new BusinessException(ResponseEnum.ILLEGAL_TOKEN);
        }
        // 替换token中的Bearer
        String simpleToken = token.replace(Consts.TOKEN_BEARER, "");
        // 解析token
        Claims claims = Jwts.parser()
                .setSigningKey(getKey())
                .parseClaimsJws(simpleToken)
                .getBody();

        // 获取用户信息
        SysUserDO user = JsonUtils.toBean(JsonUtils.toJson(claims.get(USER)), SysUserDO.class);
        // 从redis缓存中拿到token
        String jwtKey = RedisKeyFactory.getJwtKey(user.getUserId());
        String redisToken = ToolsWapper.redis().get(jwtKey);
        // 校验redis中的token是否过期
        if (redisToken == null) {
            throw new BusinessException(ResponseEnum.EXPIRE_TOKEN);
        }
        // 校验浏览器传过来的token和缓存中的token是否相等
        if (!simpleToken.equals(redisToken)) {
            throw new BusinessException(ResponseEnum.ILLEGAL_TOKEN);
        }
        List<SysRoleDO> roles = JsonUtils.toType(JsonUtils.toJson(claims.get(ROLES)), new TypeReference<List<SysRoleDO>>() {
        });
        List<SysPermissionDO> permissions = JsonUtils.toType(JsonUtils.toJson(claims.get(AUTHORITIES)), new TypeReference<List<SysPermissionDO>>() {
        });
        return RbacContext.builder().user(user).roles(roles).permissions(permissions).build();
    }

    /**
     * 设置JWT过期
     *
     * @param userId 用户ID
     */
    public static void deleteJwt(String userId) {
        // 从redis中清除JWT
        ToolsWapper.redis().delete(RedisKeyFactory.getJwtKey(userId));
    }

}

    