package com.hb.bsmanage.web.security.util;

import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.web.security.model.RbacContext;

import java.util.ArrayList;
import java.util.List;

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
    public static SysUserDO getCurrentUser() {
        return RC.get() == null ? new SysUserDO() : RC.get().getUser();
    }

    /**
     * 获取当前用户的角色信息
     *
     * @return Set<SysRoleDO>
     */
    public static List<SysRoleDO> getCurrentUserRoles() {
        return RC.get() == null ? new ArrayList<>() : RC.get().getRoles();
    }

    /**
     * 获取当前用户的权限信息
     *
     * @return Set<SysRoleDO>
     */
    public static List<SysPermissionDO> getCurrentUserPermissions() {
        return RC.get() == null ? new ArrayList<>() : RC.get().getPermissions();
    }

}
