package com.hb.bsmanage.web.common;

import com.hb.unic.cache.service.IRedisService;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 对相关工具进行包装，方便输出
 *
 * @version v0.1, 2020/7/24 15:24, create by huangbiao.
 */
@Component
public class ToolsWapper implements InitializingBean {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ToolsWapper.class);

    /**
     * redis工具
     */
    @Autowired
    private IRedisService iRedisService;
    private static IRedisService iRedisServiceAgent;

    @Override
    public void afterPropertiesSet() throws Exception {
        iRedisServiceAgent = iRedisService;
    }

    /**
     * 获取redis操作类
     *
     * @return IRedisService
     */
    public static IRedisService redis() {
        return iRedisServiceAgent;
    }

}

    