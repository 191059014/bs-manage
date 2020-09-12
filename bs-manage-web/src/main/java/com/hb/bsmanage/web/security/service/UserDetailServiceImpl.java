package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.api.ISysAccessService;
import com.hb.bsmanage.api.ISysRoleService;
import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysAccessDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
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
     * 角色service
     */
    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 权限service
     */
    @Autowired
    private ISysAccessService iSysAccessService;

    @Override
    public UserDetails loadUserByUsername(String usernameOrMobile) throws UsernameNotFoundException {
        LOGGER.info("UserDetailServiceImpl-loadUserByUsername-通过用户名加载用户信息");
        // 查询用户信息
        SysUserDO user = iSysUserService.findByUsernameOrMobile(usernameOrMobile);
        // 查询角色信息
        List<SysRoleDO> roleList = iSysRoleService.selectList(Where.build().addSingle(QueryType.EQUAL, "user_id", user.getUserId()));
        // 查询权限信息
        List<SysAccessDO> accessList = null;
        if (CollectionUtils.isNotEmpty(roleList)) {
            Set<String> roleIdSet = roleList.stream().map(SysRoleDO::getRoleId).collect(Collectors.toSet());
            accessList = iSysAccessService.selectList(Where.build().addSingle(QueryType.IN, "role_id", roleIdSet));
        }
        return new UserPrincipal(user, roleList, accessList);
    }

}

    