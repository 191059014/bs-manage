package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.bsmanage.web.service.ISysPermissionService;
import com.hb.bsmanage.web.service.ISysRoleAccessService;
import com.hb.bsmanage.web.service.ISysUserRoleService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义UserDetailsService的实现
 *
 * @author Mr.Huang
 * @version v0.1, CustomUserDetailServiceImpl.java, 2020/6/1 15:23, create by huangbiao.
 */
@Service("customUserDetailsService")
public class CustomUserDetailServiceImpl implements UserDetailsService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomUserDetailServiceImpl.class);

    /**
     * 用户service
     */
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 用户角色关系service
     */
    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    /**
     * 角色权限关系service
     */
    @Autowired
    private ISysRoleAccessService iSysRoleAccessService;

    /**
     * 权限service
     */
    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 查询用户
     *
     * @param usernameOrMobile 用户名或手机号
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) {
        String baseLog = LogHelper.getBaseLog("通过登录名加载用户");
        /*
         * 查询用户信息
         */
        SysUserPO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
        if (user == null) {
            return null;
        }
        /*
         * 查询角色信息
         */
        Set<String> roleIdSet = iSysUserRoleService.getRoleIdSetByUserId(user.getUserId());
        Set<String> permissionValues = null;
        if (CollectionUtils.isNotEmpty(roleIdSet)) {
            Set<String> permissionIdSet = iSysRoleAccessService.getPermissionIdSetByRoleIdSet(roleIdSet);
            if (CollectionUtils.isNotEmpty(permissionIdSet)) {
                List<SysPermissionPO> permissions = iSysPermissionService.getPermissionListByPermissionIdSet(permissionIdSet);
                if (CollectionUtils.isNotEmpty(permissions)) {
                    permissionValues = permissions.stream().map(SysPermissionPO::getValue).collect(Collectors.toSet());
                }
            }
        }
        LOGGER.info("{}权限信息={}", baseLog, permissionValues);
        return new UserPrincipal(user.getUserName(), user.getPassword(), permissionValues);
    }

}

    