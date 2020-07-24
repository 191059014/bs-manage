package com.hb.bsmanage.web;

import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 数据库密码加密工具
 *
 * @version v0.1, 2020/7/24 13:35, create by huangbiao.
 */
public class JasyptTest extends WebApplicationTest {

    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptPwd() {
        String password = "root3306";
        String result = stringEncryptor.encrypt(password);
        System.out.println(String.format("加密：%s => %s", password, result));
    }

}

    