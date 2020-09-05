package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.web.controller.LoginController;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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

    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        System.out.println(("UserDetailServiceImpl-loadUserByUsername====="));
        SysUserDO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
//        User user = this.getUserByUserName(s);
//        log.info("用户信息：{}", user);
//        List<String> roleList = this.getRoleListByRoleIdArr(s);
//        log.info("角色信息：{}", roleList);
//        List<Permission> permissionList = this.getPermissionListByPermissionIdArr(s);
//        log.info("权限信息：{}", permissionList);
//        return new UserPrincipal(user, roleList, permissionList);
        return new UserPrincipal(user, null, null);
    }

}

    