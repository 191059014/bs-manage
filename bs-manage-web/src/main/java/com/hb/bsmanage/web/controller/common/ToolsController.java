package com.hb.bsmanage.web.controller.common;

import com.hb.bsmanage.api.*;
import com.hb.bsmanage.model.dobj.*;
import com.hb.bsmanage.model.enums.AccessType;
import com.hb.bsmanage.model.enums.TableEnum;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.KeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    private ISysAccessService iSysAccessService;

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
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 从redis中获取key对应value
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @GetMapping("/redis/get/{key}")
    public Result<Object> getFromRedis(@PathVariable("key") String key) {
        return Result.of(ResponseEnum.SUCCESS, ToolsWapper.redis().get(key));
    }

    /**
     * 从redis中删除key
     *
     * @param key 缓存key
     * @return 缓存值
     */
    @GetMapping("/redis/clear/{key}")
    public Result<Object> clearFromRedis(@PathVariable("key") String key) {
        return Result.of(ResponseEnum.SUCCESS, ToolsWapper.redis().delete(key));
    }

    /**
     * 添加顶级商户、用户、角色、权限信息
     *
     * @return Result
     */
    @GetMapping("/addHighestSys")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result addHighestSys() {
        String baseLog = "[ToolsController-addAdmin-添加最高级别系统用户信息]";
        // 新增商户
        SysMerchantDO merchant = SysMerchantDO.builder()
                .merchantId(KeyUtils.getTenantId())
                .merchantName("一级商户")
                .build();
        iSysMerchantService.insert(merchant);
        LOGGER.info("{}添加商户成功={}", baseLog, merchant.getMerchantId());
        // 新增系统管理员用户
        SysUserDO user = SysUserDO.builder()
                .userId(KeyUtils.getUniqueKey(TableEnum.USER_ID.getIdPrefix()))
                .userName("admin")
                .password(new BCryptPasswordEncoder().encode("123456"))
                .mobile("12345678900")
                .build();
        user.setTenantId(merchant.getMerchantId());
        iSysUserService.insert(user);
        LOGGER.info("{}添加用户成功={}", baseLog, user.getUserId());
        // 新增角色
        SysRoleDO role = SysRoleDO.builder()
                .roleId(KeyUtils.getUniqueKey(TableEnum.ROLE_ID.getIdPrefix()))
                .roleName("系统管理员")
                .build();
        role.setTenantId(merchant.getMerchantId());
        iSysRoleService.insert(role);
        LOGGER.info("{}添加角色成功={}", baseLog, role.getRoleId());
        // 新增用户角色关系
        SysUserRoleDO userRole = SysUserRoleDO.builder()
                .userId(user.getUserId())
                .roleId(role.getRoleId())
                .build();
        iSysUserRoleService.insert(userRole);
        LOGGER.info("{}添加用户角色关系成功={}={}", baseLog, userRole.getUserId(), userRole.getRoleId());
        // 新增权限信息
        String[] accessNameArr = new String[]{"商户管理", "用户管理", "角色管理", "权限管理"};
        String[] urlArr = new String[]{"/merchantManage", "/defaultContent", "/defaultContent", "/defaultContent"};
        String[] iconArr = new String[]{"el-icon-location", "el-icon-location", "el-icon-location", "el-icon-location"};
        for (int i = 0; i < 4; i++) {
            SysAccessDO access = SysAccessDO.builder()
                    .accessId(KeyUtils.getUniqueKey(TableEnum.ACCESS_ID.getIdPrefix()))
                    .accessName(accessNameArr[i])
                    .accessType(AccessType.PAGE.getValue())
                    .accessValue(null)
                    .url(urlArr[i])
                    .icon(iconArr[i])
                    .build();
            iSysAccessService.insert(access);
            LOGGER.info("{}添加权限成功={}", baseLog, access.getAccessId());
            // 新增角色权限关系信息
            SysRoleAccessDO roleAccess = SysRoleAccessDO.builder()
                    .roleId(role.getRoleId())
                    .accessId(access.getAccessId())
                    .build();
            iSysRoleAccessService.insert(roleAccess);
            LOGGER.info("{}添加角色权限关系成功={}={}", baseLog, role.getRoleId(), access.getAccessId());
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

}

    