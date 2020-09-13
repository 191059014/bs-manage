package com.hb.bsmanage.web.security.util;

import com.hb.bsmanage.model.dobj.SysAccessDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.unic.util.tool.Assert;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * 工具类
 *
 * @author guoll
 * @date 2020/9/12
 */
public class SecurityUtils {

    /**
     * 获取当前用户信息
     *
     * @return SysUserDO
     */
    public static SysUserDO getCurrentUser() {
        UserPrincipal userPrincipal = getUserPrincipal();
        return userPrincipal.getUser();
    }

    /**
     * 获取当前用户的多租户ID
     *
     * @return 多租户ID
     */
    public static String getCurrentUserTenantId() {
        return getCurrentUser().getTenantId();
    }

    /**
     * 获取当前用户的角色信息
     *
     * @return Set<SysRoleDO>
     */
    public static List<SysRoleDO> getCurrentUserRoles() {
        UserPrincipal userPrincipal = getUserPrincipal();
        return userPrincipal.getRoles();
    }

    /**
     * 获取当前用户的权限信息
     *
     * @return Set<SysRoleDO>
     */
    public static List<SysAccessDO> getCurrentUserAccesses() {
        UserPrincipal userPrincipal = getUserPrincipal();
        return userPrincipal.getAccesses();
    }

    /**
     * 获取UserPrincipal信息
     *
     * @return UserPrincipal
     */
    private static UserPrincipal getUserPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        Assert.notNull(principal, "从Security上下文获取Principal信息为空");
        return (UserPrincipal) principal;
    }

}
