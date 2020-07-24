package com.hb.bsmanage.web;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试入口
 *
 * @version v0.1, 2020/7/24 13:27, create by huangbiao.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WebApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebApplicationTest {

    @Before
    public void before() {
        System.out.println("test before...");
    }

    @After
    public void after() {
        System.out.println("test after...");
    }

}

    