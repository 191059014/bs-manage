package com.hb.bsmanage.web.common;

import com.hb.bsmanage.model.common.Consts;

/**
 * redis缓存key管理工厂
 *
 * @author Mr.Huang
 * @version v0.1, RedisKeyFactory.java, 2020/6/19 15:29, create by huangbiao.
 */
public class RedisKeyFactory {

    /**
     * 获取jwt的token缓存键
     *
     * @param userId 用户id
     * @return key
     */
    public static String getJwtKey(String userId) {
        return new StringBuilder(Consts.projectName).append("jwt:token:").append(userId).toString();
    }

}

    