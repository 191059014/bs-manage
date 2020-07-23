package com.hb.bsmanage.web.controller;

import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @version v0.1, 2020/7/23 17:14, create by huangbiao.
 */
@RestController
@RequestMapping("/controller/test")
public class ControllerTest implements InitializingBean {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);

    @GetMapping("/test")
    public void test() {
        LOGGER.info("测试");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("afterPropertiesSet");
    }
}

    