package com.hb.bsmanage.web.security.util;

import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.security.model.RbacContext;

import java.util.HashSet;
import java.util.Set;

/**
 * 工具类
 *
 * @author guoll
 * @date 2020/9/12
 */
public class SecurityUtils {

    /**
     * rbac信息绑定当前线程
     */
    private static ThreadLocal<RbacContext> RC = new InheritableThreadLocal<>();

    /**
     * 设置当前线程绑定的rbac信息
     *
     * @param rbacContext rbac信息
     */
    public static void setRbacContext(RbacContext rbacContext) {
        RC.set(rbacContext);
    }

    /**
     * 获取当前用户信息
     *
     * @return SysUserDO
     */
    public static SysUserPO getCurrentUser() {
        return RC.get() == null ? new SysUserPO() : RC.get().getUser();
    }

    /**
     * 获取当前用户的角色信息
     *
     * @return 角色id集合
     */
    public static Set<String> getCurrentUserRoles() {
        return RC.get() == null ? new HashSet<>() : RC.get().getRoles();
    }

    /**
     * 获取当前用户的权限信息
     *
     * @return 权限id集合
     */
    public static Set<String> getCurrentUserPermissions() {
        return RC.get() == null ? new HashSet<>() : RC.get().getPermissions();
    }

    /**
     * 获取当前用户的用户ID
     *
     * @return UserId
     */
    public static String getCurrentUserId() {
        return getCurrentUser().getUserId();
    }

    /**
     * 获取当前用户的用户名称
     *
     * @return UserId
     */
    public static String getCurrentUserName() {
        return getCurrentUser().getUserName();
    }


    /**
     * 获取当前用户的TenantId
     *
     * @return TenantId
     */
    public static String getCurrentUserTenantId() {
        return RC.get() == null ? "" : RC.get().getUser() == null ? "" : RC.get().getUser().getTenantId();
    }

}
