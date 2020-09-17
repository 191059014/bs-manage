package com.hb.bsmanage.web.security.controller;

import com.hb.bsmanage.api.ISysPermissionService;
import com.hb.bsmanage.api.ISysRoleAccessService;
import com.hb.bsmanage.api.ISysRoleService;
import com.hb.bsmanage.api.ISysUserRoleService;
import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.request.LoginRequest;
import com.hb.bsmanage.web.common.BaseController;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.security.jwt.JwtUtils;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.logger.util.LogExceptionWapper;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登陆
 *
 * @version v0.1, 2020/9/4 14:51, create by huangbiao.
 */
@RestController
@RequestMapping("bs/noauth/login")
public class LoginController extends BaseController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    /**
     * 认证管理器
     */
    @Autowired
    private AuthenticationManager authenticationManager;

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
     * 登陆
     *
     * @param req 登录信息
     * @return jwt令牌
     */
    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginRequest req) {
        String baseLog = "[LoginController-login-登陆]";
        if (StringUtils.isAnyBlank(req.getUsernameOrMobile(), req.getPassword())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        try {
            // 登陆认证
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsernameOrMobile(), req.getPassword()));
            // 从认证信息中获取用户信息
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String username = userPrincipal.getUsername();
            SysUserDO user = iSysUserService.findByUsernameOrMobile(username);
            Set<String> roleIdSet = iSysUserRoleService.getRoleIdSetByUserId(user.getUserId());
            List<SysRoleDO> roles = null;
            Set<String> permissionIdSet = null;
            if (CollectionUtils.isNotEmpty(roleIdSet)) {
                roles = iSysRoleService.getRoleListByRoleIdSet(roleIdSet);
                permissionIdSet = iSysRoleAccessService.getPermissionIdSetByRoleIdSet(roleIdSet);
            }
            List<SysPermissionDO> permissions = null;
            if (CollectionUtils.isNotEmpty(permissionIdSet)) {
                permissions = iSysPermissionService.getPermissionListByPermissionIdSet(permissionIdSet);
            }
            String jwt = JwtUtils.createToken(user, roles, permissions, req.isRememberMe());
            return Result.of(ResponseEnum.SUCCESS, jwt);
        } catch (BadCredentialsException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}用户名或密码错误={}", baseLog, LogExceptionWapper.getStackTrace(e));
            }
            return Result.of(ResponseEnum.BAD_CREDENTIALS);
        }


    }

}

    