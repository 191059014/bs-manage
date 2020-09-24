package com.hb.bsmanage.web.security.util;

import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.security.model.RbacContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 清空当前线程绑定的rbac信息
     */
    public static void clearRbacContext() {
        RC.remove();
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
     * 获取当前用户的角色集合
     *
     * @return 角色id集合
     */
    public static List<SysRolePO> getCurrentUserRoles() {
        return RC.get() == null ? new ArrayList<>() : RC.get().getRoles();
    }

    /**
     * 获取当前用户的角色ID集合
     *
     * @return 角色id集合
     */
    public static Set<String> getCurrentUserRoleIdSet() {
        return getCurrentUserRoles().stream().map(SysRolePO::getRoleId).collect(Collectors.toSet());
    }

    /**
     * 获取当前用户的权限集合
     *
     * @return 权限id集合
     */
    public static List<SysPermissionPO> getCurrentUserPermissions() {
        return RC.get() == null ? new ArrayList<>() : RC.get().getPermissions();
    }

    /**
     * 获取当前用户的权限ID集合
     *
     * @return 权限id集合
     */
    public static Set<String> getCurrentUserPermissionIdSet() {
        return getCurrentUserPermissions().stream().map(SysPermissionPO::getPermissionId).collect(Collectors.toSet());
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

    /**
     * 获取当前用户的ParentId
     *
     * @return ParentId
     */
    public static String getCurrentUserParentId() {
        return RC.get() == null ? "" : RC.get().getUser() == null ? "" : RC.get().getUser().getParentId();
    }

}
