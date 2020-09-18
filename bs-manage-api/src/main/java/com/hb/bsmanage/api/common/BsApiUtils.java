package com.hb.bsmanage.api.common;

import com.hb.bsmanage.model.dobj.SysMerchantDO;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.StrUtils;

/**
 * 工具类
 *
 * @version v0.1, 2020/9/17 13:35, create by huangbiao.
 */
public class BsApiUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BsApiUtils.class);

    /**
     * 获取当前level
     *
     * @param levelOfParent 父级的level
     * @param idOfParent    父级的id
     * @return level
     */
    public static String getCurrentLevel(Object levelOfParent, Object idOfParent) {
        return StrUtils.joint(levelOfParent, Consts.DOT, idOfParent);
    }

    /**
     * 获取子级level的前缀
     *
     * @param levelOfCurrent 当前的level
     * @return level
     */
    public static String getSubLevelPrefix(Object levelOfCurrent) {
        return StrUtils.joint(levelOfCurrent, Consts.DOT);
    }

    /**
     * 获取子级商户level的where条件
     *
     * @param where               where条件
     * @param currentUserMerchant 当前的商户
     * @return Where
     */
    public static Where getSubLevelWhere(Where where, SysMerchantDO currentUserMerchant) {
        where
                .and()
                .leftBracket()
                .add(QueryType.EQUAL, "merchant_id", currentUserMerchant.getMerchantId())
                .or()
                .add(QueryType.LIKE, "level", getSubLevelPrefix(currentUserMerchant.getLevel()))
                .rightBracket();
        return where;
    }

}

    