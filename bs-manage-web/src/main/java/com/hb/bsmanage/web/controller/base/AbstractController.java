package com.hb.bsmanage.web.controller.base;

import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.unic.base.common.ResponseData;
import com.hb.unic.base.util.ResponseUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.logger.util.LogExceptionWapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * controller抽象类
 *
 * @version v0.1, 2020/7/24 15:07, create by huangbiao.
 */
public abstract class AbstractController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);

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
        Map<String, Object> param = new HashMap<>();
        Enumeration e = request.getParameterNames();
        // 循环获取参数
        while (e.hasMoreElements()) {
            String paramterName = e.nextElement().toString();
            param.put(paramterName, request.getParameter(paramterName));
        }
        return param;
    }

    /**
     * 异常统一处理
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseData exception(Exception e) {
        String baseLog = "[统一异常处理]";
        LOGGER.error("{}{}", baseLog, LogExceptionWapper.getStackTrace(e));
        return ResponseUtils.generateResponseData(ResponseEnum.ERROR);
    }

}

    