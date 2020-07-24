package com.hb.bsmanage.web.security.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * spring-security自定义配置
 *
 * @version v0.1, 2020/7/24 17:05, create by huangbiao.
 */
@Component
@ConfigurationProperties(prefix = "security")
@Setter
@Getter
public class SecurityProperties {

    /**
     * 忽略的url请求
     */
    private Set<String> ignoreUrlPatterns;

}

    