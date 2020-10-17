package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.common.RedisKeyFactory;
import com.hb.bsmanage.web.common.ToolsWapper;
import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.bsmanage.web.common.util.BsWebUtils;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.model.vo.LoginResponse;
import com.hb.bsmanage.web.security.model.RbacContext;
import com.hb.bsmanage.web.security.model.UserPrincipal;
import com.hb.bsmanage.web.service.*;
import com.hb.unic.base.GlobalProperties;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.base.util.ServletUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

/**
 * 登陆成功处理器
 *
 * @version v0.1, 2020/10/16 16:56, create by huangbiao.
 */
@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        ServletUtils.writeResponse(httpServletResponse,JsonUtils.toJson(Result.of(ErrorCode.SUCCESS)));
    }
}

    