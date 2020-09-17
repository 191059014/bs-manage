package com.hb.bsmanage.web.controller;

import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.base.exception.StandardRuntimeException;
import com.hb.unic.base.util.ServletUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.logger.util.LogExceptionWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * controller抽象类
 *
 * @version v0.1, 2020/7/24 15:07, create by huangbiao.
 */
public abstract class BaseController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseController.class);

    /**
     * HttpServletRequest
     */
    @Autowired
    protected HttpServletRequest request;

    /**
     * HttpServletResponse
     */
    @Autowired
    protected HttpServletResponse response;

    /**
     * 获取get请求参数
     *
     * @return 参数集合
     */
    protected Map<String, Object> getUrlParams() {
        return ServletUtils.getParameterMap(request);
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public Result exception(BusinessException e) {
        String baseLog = "[统一异常处理-业务异常]";
        LOGGER.error("{}{}", baseLog, LogExceptionWapper.getStackTrace(e));
        return Result.of(e.getKey(), e.getMessage());
    }

    /**
     * 标准异常
     */
    @ExceptionHandler(StandardRuntimeException.class)
    @ResponseBody
    public Result exception(StandardRuntimeException e) {
        String baseLog = "[统一异常处理-标准异常]";
        LOGGER.error("{}{}", baseLog, LogExceptionWapper.getStackTrace(e));
        return Result.of(e.getKey(), e.getMessage());
    }

    /**
     * 系统异常Exception
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e) {
        String baseLog = "[统一异常处理-系统异常]";
        LOGGER.error("{}{}", baseLog, LogExceptionWapper.getStackTrace(e));
        return Result.of(ResponseEnum.ERROR);
    }

}

    