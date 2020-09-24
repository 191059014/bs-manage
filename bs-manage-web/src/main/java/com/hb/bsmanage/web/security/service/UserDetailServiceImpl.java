package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.bsmanage.web.service.ISysRoleAccessService;
import com.hb.bsmanage.web.service.ISysUserRoleService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.base.util.LogHelper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;

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
        String baseLog = LogHelper.getBaseLog("登录认证");
        /*
         * 查询用户信息
         */
        SysUserPO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
        LOGGER.info("{}用户信息={}", baseLog, user);
        if (user == null) {
            return null;
        }
        /*
         * 查询角色信息
         */
        Set<String> roleIdSet = iSysUserRoleService.getRoleIdSetByUserId(user.getUserId());
        Set<String> permissionIdSet = null;
        if (CollectionUtils.isNotEmpty(roleIdSet)) {
            permissionIdSet = iSysRoleAccessService.getPermissionIdSetByRoleIdSet(roleIdSet);
        }
        LOGGER.info("{}角色信息={}", baseLog, permissionIdSet);
        return new UserPrincipal(user.getUserName(), user.getPassword(), permissionIdSet);
    }

}

    