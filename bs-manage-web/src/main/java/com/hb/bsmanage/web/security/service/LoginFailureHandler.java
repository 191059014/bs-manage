package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.util.ServletUtils;
import com.hb.unic.util.util.JsonUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功处理器
 *
 * @version v0.1, 2020/10/16 16:56, create by huangbiao.
 */
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        ServletUtils.writeResponse(httpServletResponse, JsonUtils.toJson(Result.of(ErrorCode.BAD_CREDENTIALS)));
    }

}

    