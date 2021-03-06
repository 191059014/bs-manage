package com.hb.bsmanage.web;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * 项目入口
 */
@SpringBootApplication
@ImportResource({"classpath*:META-INF/applicationContext-web.xml"})
@EnableEncryptableProperties
public class WebApplication {

    /**
     * main方法
     *
     * @param args 启动参数
     */
    public static void main(String[] args) {
        ConfigurableApplicationContext applicationContext = SpringApplication.run(WebApplication.class, args);
        ConfigurableEnvironment env = applicationContext.getEnvironment();
        String host = env.getProperty("server.address");
        String port = env.getProperty("server.port");
        System.out.println("===========================================================================");
        System.out.println(String.format(" you can enjoy yourself, more please see: http://%s:%s/index.html", host == null ? "localhost" : host, port));
        System.out.println("===========================================================================");
    }

    /**
     * 开启测试模式
     *
     * @return true为测试模式
     */
    public static boolean testModeIsOpen() {
        return true;
    }

}
