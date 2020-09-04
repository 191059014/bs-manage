package com.hb.bsmanage.web.controller;

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
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.stream.Collectors;

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
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsernameOrMobile(), req.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            SysUserDO sysUser = userPrincipal.getUser();
            Set<SysRoleDO> roles = userPrincipal.getRoles();
            String jwt = JwtUtils.createToken(sysUser.getUserId(), sysUser.getUserName(), roles == null ? null : roles.stream().map(SysRoleDO::getRoleId).collect(Collectors.toList()), userPrincipal.getAuthorities(), req.isRememberMe());
            return Result.of(ResponseEnum.SUCCESS, jwt);
        } catch (BadCredentialsException e) {
            if (LOGGER.isErrorEnabled()) {
                LOGGER.error("{}证书错误={}", baseLog, LogExceptionWapper.getStackTrace(e));
            }
            return Result.of(ResponseEnum.PWD_ERROR);
        }


    }

}

    