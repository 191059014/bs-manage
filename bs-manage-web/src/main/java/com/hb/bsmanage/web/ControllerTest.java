package com.hb.bsmanage.web;

import com.hb.bsmanage.web.common.BaseController;
import com.hb.unic.cache.service.IRedisService;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试
 *
 * @version v0.1, 2020/7/23 17:14, create by huangbiao.
 */
@RestController
@RequestMapping("bs/noauth/test")
public class ControllerTest extends BaseController implements InitializingBean {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerTest.class);

    @Autowired
    private IRedisService iRedisService;

    @GetMapping("/testGet")
    public void testGet() {
        getUrlParams();
        LOGGER.info("get测试");
    }

    @PostMapping("/testPost")
    public void testPost(String json) {
        getUrlParams();
        LOGGER.info("post测试: {}", json);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOGGER.info("afterPropertiesSet");
        iRedisService.set("name", "zhangsan", 15);
    }
}

    