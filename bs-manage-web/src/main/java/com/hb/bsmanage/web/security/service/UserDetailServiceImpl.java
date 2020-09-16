package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.api.ISysPermissionService;
import com.hb.bsmanage.api.ISysRoleAccessService;
import com.hb.bsmanage.api.ISysRoleService;
import com.hb.bsmanage.api.ISysUserRoleService;
import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysRolePermissionDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.dobj.SysUserRoleDO;
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

    /**
     * 登录认证
     *
     * @param usernameOrMobile 用户名或手机号
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        String baseLog = "[UserDetailServiceImpl-loadUserByUsername-登陆认证]";
        // 查询用户信息
        SysUserDO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
        LOGGER.info("{}通过用户名或手机号查询用户={}", baseLog, user);
        if (user == null) {
            LOGGER.info("{}无此用户={}", baseLog, usernameOrMobile);
            throw new UsernameNotFoundException("无此用户：" + usernameOrMobile);
        }
        // 查询角色信息
        List<SysUserRoleDO> userRoleList = iSysUserRoleService.selectList(Where.build().add(QueryType.EQUAL, "user_id", user.getUserId()));
        LOGGER.info("{}通过用户ID查询用户角色关系列表={}", baseLog, userRoleList);
        // 查询权限信息
        List<SysPermissionDO> accessList = null;
        List<SysRoleDO> roleList = null;
        if (CollectionUtils.isNotEmpty(userRoleList)) {
            Set<String> roleIdSet = userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
            roleList = iSysRoleService.selectList(Where.build().add(QueryType.IN, "role_id", roleIdSet));
            LOGGER.info("{}通过角色ID集合查询角色列表={}", baseLog, roleList);
            List<SysRolePermissionDO> roleAccessList = iSysRoleAccessService.selectList(Where.build().add(QueryType.IN, "role_id", roleIdSet));
            LOGGER.info("{}通过角色ID集合查询角色权限关系列表={}", baseLog, roleAccessList);
            if (CollectionUtils.isNotEmpty(roleAccessList)) {
                Set<String> accessIdSet = roleAccessList.stream().map(SysRolePermissionDO::getPermissionId).collect(Collectors.toSet());
                accessList = iSysPermissionService.selectList(Where.build().add(QueryType.IN, "permission_id", accessIdSet));
                LOGGER.info("{}通过权限ID集合查询权限列表={}", baseLog, accessList);
            }
        }
        return new UserPrincipal(user, roleList, accessList);
    }

}

    