package com.hb.bsmanage.web.security.controller;

import com.hb.bsmanage.web.service.*;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.model.vo.LoginRequest;
import com.hb.bsmanage.web.common.enums.ResponseEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.security.jwt.JwtUtils;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.logger.util.LogExceptionWapper;
import com.hb.unic.logger.util.LogHelper;
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
        String baseLog = LogHelper.getBaseLog("登录");
        LOGGER.info("{}入参={}", baseLog, req);
        if (StringUtils.isAnyBlank(req.getUsernameOrMobile(), req.getPassword())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        try {
            // 登陆认证
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsernameOrMobile(), req.getPassword()));
            // 从认证信息中获取用户信息
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            String username = userPrincipal.getUsername();
            SysUserPO user = iSysUserService.findByUsernameOrMobile(username);
            if (user == null) {
                return Result.of(ResponseEnum.USER_NOT_EXIST);
            }
            Set<String> roleIdSet = iSysUserRoleService.getRoleIdSetByUserId(user.getUserId());
            Set<String> permissionIdSet = null;
            if (CollectionUtils.isNotEmpty(roleIdSet)) {
                permissionIdSet = iSysRoleAccessService.getPermissionIdSetByRoleIdSet(roleIdSet);
            }
            String jwt = JwtUtils.createToken(user, roleIdSet, permissionIdSet, req.isRememberMe());
            return Result.of(ResponseEnum.SUCCESS, jwt);
        } catch (BadCredentialsException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}用户名或密码错误={}", baseLog, LogExceptionWapper.getStackTrace(e));
            }
            return Result.of(ResponseEnum.BAD_CREDENTIALS);
        }
    }

}

    