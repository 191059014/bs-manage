package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.api.service.ISysPermissionService;
import com.hb.bsmanage.api.service.ISysRoleAccessService;
import com.hb.bsmanage.api.service.ISysUserRoleService;
import com.hb.bsmanage.api.service.ISysUserService;
import com.hb.bsmanage.model.po.SysPermissionPO;
import com.hb.bsmanage.model.po.SysUserPO;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户信息
 *
 * @author Mr.Huang
 * @version v0.1, UserDetailServiceImpl.java, 2020/6/1 15:23, create by huangbiao.
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailServiceImpl.class);

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
     * 权限service
     */
    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 角色权限关系service
     */
    @Autowired
    private ISysRoleAccessService iSysRoleAccessService;

    /**
     * 登录认证
     *
     * @param usernameOrMobile 用户名或手机号
     * @return UserDetails
     */
    @Override
    @InOutLog("登陆认证")
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        // 查询用户信息
        SysUserPO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
        if (user == null) {
            LOGGER.info("无此用户={}", usernameOrMobile);
            throw new UsernameNotFoundException("无此用户：" + usernameOrMobile);
        }
        // 查询角色信息
        Set<String> roleIdSet = iSysUserRoleService.getRoleIdSetByUserId(user.getUserId());
        Set<String> permissionIdSet = null;
        if (CollectionUtils.isNotEmpty(roleIdSet)) {
            permissionIdSet = iSysRoleAccessService.getPermissionIdSetByRoleIdSet(roleIdSet);
        }
        List<SysPermissionPO> permissionsList = null;
        if (CollectionUtils.isNotEmpty(permissionIdSet)) {
            permissionsList = iSysPermissionService.getPermissionListByPermissionIdSet(permissionIdSet);
        }
        List<String> permissions = null;
        if (CollectionUtils.isNotEmpty(permissionsList)) {
            permissions = permissionsList.stream().map(SysPermissionPO::getValue).collect(Collectors.toList());
        }
        return new UserPrincipal(user.getUserName(), user.getPassword(), permissions);
    }

}

    