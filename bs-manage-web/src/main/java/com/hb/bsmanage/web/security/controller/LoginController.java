package com.hb.bsmanage.web.security.controller;

import com.hb.bsmanage.web.common.RedisKeyFactory;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.bsmanage.web.common.util.BsWebUtils;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.model.vo.LoginRequest;
import com.hb.bsmanage.web.model.vo.LoginResponse;
import com.hb.bsmanage.web.security.model.RbacContext;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.bsmanage.web.service.ISysPermissionService;
import com.hb.bsmanage.web.service.ISysRoleAccessService;
import com.hb.bsmanage.web.service.ISysRoleService;
import com.hb.bsmanage.web.service.ISysUserRoleService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.unic.base.GlobalProperties;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.util.LogExceptionWapper;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
     * @param req 登录参数
     * @return LoginResponse
     */
    @PostMapping("/login")
    public Result<LoginResponse> login(@RequestBody LoginRequest req) {
        String baseLog = LogHelper.getBaseLog("登录");
        LOGGER.info("{}入参={}", baseLog, req);
        if (StringUtils.isAnyBlank(req.getUsernameOrMobile(), req.getPassword())) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        Authentication authentication = null;
        try {
            // 登陆认证
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsernameOrMobile(), req.getPassword()));
        } catch (AuthenticationException e) {
            LOGGER.error("{}登陆认证异常={}", baseLog, LogExceptionWapper.getStackTrace(e));
            return Result.of(ErrorCode.BAD_CREDENTIALS);
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 从认证信息中获取用户信息
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String username = userPrincipal.getUsername();
        /*
         * 加载用户信息
         */
        SysUserPO user = iSysUserService.findByUsernameOrMobile(username);
        LOGGER.info("{}用户信息={}", baseLog, user);
        if (user == null) {
            return Result.of(ErrorCode.USER_NOT_EXIST);
        }
        /*
         * 加载角色信息
         */
        Set<String> roleIdSet = iSysUserRoleService.getRoleIdSetByUserId(user.getUserId());
        List<SysRolePO> roles = iSysRoleService.getRoleListByRoleIdSet(roleIdSet);
        LOGGER.info("{}角色信息={}", baseLog, roleIdSet);
        /*
         * 加载权限信息
         */
        Set<String> permissionIdSet = iSysRoleAccessService.getPermissionIdSetByRoleIdSet(roleIdSet);
        List<SysPermissionPO> permissions = iSysPermissionService.getPermissionListByPermissionIdSet(permissionIdSet);
        LOGGER.info("{}权限信息={}", baseLog, permissionIdSet);
        /*
         * 组装rbac信息
         */
        RbacContext rbacContext = RbacContext.builder().user(user).roles(roles).permissions(permissions).build();
        /*
         * 将rbac信息放进缓存
         */
        String token = BsWebUtils.createToken();
        String tokenKey = RedisKeyFactory.getTokenKey(token);
        ToolsWapper.redis().set(tokenKey, rbacContext, GlobalProperties.getLong("token.defaultTtl"));
        LOGGER.info("{}rbac信息放入缓存完成={}", baseLog, tokenKey);
        /*
         * 返回信息
         */
        LoginResponse response = LoginResponse.builder().token(token).username(user.getUserName()).build();
        LOGGER.info("{}出参={}", baseLog, response);
        return Result.of(ErrorCode.SUCCESS, response);
    }

}

    