package com.hb.bsmanage.web.controller;

import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.bsmanage.web.common.enums.ResourceType;
import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.bsmanage.web.common.enums.PkPrefix;
import com.hb.bsmanage.web.common.enums.YesOrNo;
import com.hb.bsmanage.web.common.util.BsWebUtils;
import com.hb.bsmanage.web.dao.po.SysMerchantPO;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysRolePermissionPO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.dao.po.SysUserRolePO;
import com.hb.bsmanage.web.service.ISysMerchantService;
import com.hb.bsmanage.web.service.ISysPermissionService;
import com.hb.bsmanage.web.service.ISysRoleAccessService;
import com.hb.bsmanage.web.service.ISysRoleService;
import com.hb.bsmanage.web.service.ISysUserRoleService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 通用controller
 *
 * @version v0.1, 2020/9/3 18:01, create by huangbiao.
 */
@RestController
@RequestMapping("bs/noauth/tools")
public class ToolsController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolsController.class);

    /**
     * 商户
     */
    @Autowired
    private ISysMerchantService iSysMerchantService;

    /**
     * 用户
     */
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 角色
     */
    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 用户角色关系
     */
    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    /**
     * 权限
     */
    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 角色权限关系
     */
    @Autowired
    private ISysRoleAccessService iSysRoleAccessService;

    /**
     * 往redis缓存里设置值
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @RequestMapping("/redis/set/{key}/{expire}")
    public Result setToRedis(@PathVariable("key") String key,
                             @PathVariable("expire") Long expire,
                             @RequestParam(required = false, name = "value") String value,
                             @RequestBody(required = false) String json) {
        ToolsWapper.redis().set(key, value == null ? json : value, expire);
        return Result.of(ErrorCode.SUCCESS);
    }

    /**
     * 从redis中获取key对应value
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @GetMapping("/redis/get/{key}")
    public Result<Object> getFromRedis(@PathVariable("key") String key) {
        return Result.of(ErrorCode.SUCCESS, ToolsWapper.redis().get(key));
    }

    /**
     * 从redis中删除key
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @GetMapping("/redis/clear/{key}")
    public Result<Object> clearFromRedis(@PathVariable("key") String key) {
        return Result.of(ErrorCode.SUCCESS, ToolsWapper.redis().delete(key));
    }

    /**
     * 添加顶级商户、用户、角色、权限信息
     *
     * @return Result
     */
    @GetMapping("/addHighestSys")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result addHighestSys() throws Exception {
        String baseLog = "[ToolsController-addAdmin-添加最高级别系统用户信息]";
        // 新增商户
        SysMerchantPO merchant = SysMerchantPO.builder()
                .merchantId(BsWebUtils.getTenantId())
                .merchantName("一级商户")
                .build();
        merchant.setPath("0");
        iSysMerchantService.insert(merchant);
        LOGGER.info("{}添加商户成功={}", baseLog, merchant.getMerchantId());
        // 新增系统管理员用户
        SysUserPO user = SysUserPO.builder()
                .userId(KeyUtils.getUniqueKey(PkPrefix.USER_ID.getValue()))
                .userName("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .mobile("12345678900")
                .build();
        user.setTenantId(merchant.getMerchantId());
        iSysUserService.insert(user);
        LOGGER.info("{}添加用户成功={}", baseLog, user.getUserId());
        // 新增角色
        SysRolePO role = SysRolePO.builder()
                .roleId(KeyUtils.getUniqueKey(PkPrefix.ROLE_ID.getValue()))
                .roleName("系统管理员")
                .build();
        role.setTenantId(merchant.getMerchantId());
        iSysRoleService.insert(role);
        LOGGER.info("{}添加角色成功={}", baseLog, role.getRoleId());
        // 新增用户角色关系
        SysUserRolePO userRole = SysUserRolePO.builder()
                .userId(user.getUserId())
                .roleId(role.getRoleId())
                .build();
        iSysUserRoleService.insert(userRole);
        LOGGER.info("{}添加用户角色关系成功={}={}", baseLog, userRole.getUserId(), userRole.getRoleId());
        // 新增权限信息
        SysPermissionPO sysPermission = SysPermissionPO.builder()
                .permissionId(KeyUtils.getUniqueKey(PkPrefix.PERMISSION_ID.getValue()))
                .permissionName("系统管理")
                .resourceType(ResourceType.FOLDER.getValue())
                .value("sys")
                .icon("el-icon-setting")
                .build();
        sysPermission.setTenantId(merchant.getMerchantId());
        iSysPermissionService.insert(sysPermission);
        LOGGER.info("{}添加系统管理菜单成功={}", baseLog, sysPermission.getPermissionId());
        // 新增角色权限关系信息
        SysRolePermissionPO sysRolePermission = SysRolePermissionPO.builder()
                .roleId(role.getRoleId())
                .permissionId(sysPermission.getPermissionId())
                .build();
        sysRolePermission.setTenantId(merchant.getMerchantId());
        iSysRoleAccessService.insert(sysRolePermission);
        LOGGER.info("{}添加系统管理菜单角色权限关系成功={}={}", baseLog, role.getRoleId(), sysRolePermission.getPermissionId());

        String[] accessNameArr = new String[]{"商户管理", "用户管理", "角色管理", "权限管理"};
        String[] urlArr = new String[]{"/merchantManage", "/userManage", "/roleManage", "/accessManage"};
        String[] iconArr = new String[]{"el-icon-coin", "el-icon-s-custom", "el-icon-user", "el-icon-lock"};
        String[] valuePrefixArr = new String[]{"sys_merchant", "sys_user", "sys_role", "sys_permission"};
        for (int i = 0; i < 4; i++) {
            SysPermissionPO pagePermission = SysPermissionPO.builder()
                    .permissionId(KeyUtils.getUniqueKey(PkPrefix.PERMISSION_ID.getValue()))
                    .permissionName(accessNameArr[i])
                    .resourceType(ResourceType.PAGE.getValue())
                    .value(valuePrefixArr[i])
                    .url(urlArr[i])
                    .icon(iconArr[i])
                    .keepAlive(YesOrNo.YES.getValue())
                    .build();
            pagePermission.setTenantId(merchant.getMerchantId());
            pagePermission.setParentId(sysPermission.getPermissionId());
            iSysPermissionService.insert(pagePermission);
            LOGGER.info("{}添加权限成功={}", baseLog, pagePermission.getPermissionId());
            // 新增角色权限关系信息
            SysRolePermissionPO pageRolePermission = SysRolePermissionPO.builder()
                    .roleId(role.getRoleId())
                    .permissionId(pagePermission.getPermissionId())
                    .build();
            pageRolePermission.setTenantId(merchant.getMerchantId());
            iSysRoleAccessService.insert(pageRolePermission);
            LOGGER.info("{}添加角色权限关系成功={}={}", baseLog, role.getRoleId(), pagePermission.getPermissionId());
            String[] buttonName = new String[]{"新增", "修改", "删除"};
            String[] valueSuffixArr = new String[]{"_add", "_update", "_delete"};
            for (int j = 0; j < 3; j++) {
                SysPermissionPO buttonPermission = SysPermissionPO.builder()
                        .permissionId(KeyUtils.getUniqueKey(PkPrefix.PERMISSION_ID.getValue()))
                        .permissionName(buttonName[j])
                        .resourceType(ResourceType.BUTTON.getValue())
                        .value(valuePrefixArr[i] + valueSuffixArr[j])
                        .build();
                buttonPermission.setTenantId(merchant.getMerchantId());
                buttonPermission.setParentId(pagePermission.getPermissionId());
                iSysPermissionService.insert(buttonPermission);
                LOGGER.info("{}添加按钮权限成功={}", baseLog, buttonPermission.getPermissionId());
                // 新增角色权限关系信息
                SysRolePermissionPO buttonRolePermission = SysRolePermissionPO.builder()
                        .roleId(role.getRoleId())
                        .permissionId(buttonPermission.getPermissionId())
                        .build();
                buttonRolePermission.setTenantId(merchant.getMerchantId());
                iSysRoleAccessService.insert(buttonRolePermission);
                Thread.sleep(500);
            }
        }
        return Result.of(ErrorCode.SUCCESS);
    }

}

    