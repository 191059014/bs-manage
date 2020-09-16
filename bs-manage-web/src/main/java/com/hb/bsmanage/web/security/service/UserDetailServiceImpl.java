package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.api.*;
import com.hb.bsmanage.model.dobj.*;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
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
     * 角色service
     */
    @Autowired
    private ISysRoleService iSysRoleService;

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

    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        String baseLog = "[UserDetailServiceImpl-loadUserByUsername-通过用户名加载用户信息]";
        LOGGER.info(baseLog);
        // 查询用户信息
        SysUserDO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
        if (user == null) {
            LOGGER.info("{}无此用户={}", baseLog, usernameOrMobile);
            throw new UsernameNotFoundException("无此用户：" + usernameOrMobile);
        }
        // 查询角色信息
        List<SysUserRoleDO> userRoleList = iSysUserRoleService.selectList(Where.build().add(QueryType.EQUAL, "user_id", user.getUserId()));
        // 查询权限信息
        List<SysPermissionDO> accessList = null;
        List<SysRoleDO> roleList = null;
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            Set<String> roleIdSet = userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
            roleList = iSysRoleService.selectList(Where.build().add(QueryType.IN, "role_id", roleIdSet));
            List<SysRolePermissionDO> roleAccessList = iSysRoleAccessService.selectList(Where.build().add(QueryType.IN, "role_id", roleIdSet));
            if (CollectionUtils.isNotEmpty(roleAccessList)) {
                Set<String> accessIdSet = roleAccessList.stream().map(SysRolePermissionDO::getPermissionId).collect(Collectors.toSet());
                accessList = iSysPermissionService.selectList(Where.build().add(QueryType.IN, "permission_id", accessIdSet));
            }
        }
        return new UserPrincipal(user, roleList, accessList);
    }

}

    