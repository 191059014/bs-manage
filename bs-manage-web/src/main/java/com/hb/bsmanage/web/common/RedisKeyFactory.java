package com.hb.bsmanage.web.common;

import com.hb.bsmanage.web.common.constans.Consts;

/**
 * redis缓存key管理工厂
 *
 * @author Mr.Huang
 * @version v0.1, RedisKeyFactory.java, 2020/6/19 15:29, create by huangbiao.
 */
public class RedisKeyFactory {

    /**
     * 获取token缓存键
     *
     * @param token 用户id
     * @return key
     */
    public static String getTokenKey(String token) {
        return new StringBuilder(Consts.projectName).append(":token:").append(token).toString();
    }

}

    