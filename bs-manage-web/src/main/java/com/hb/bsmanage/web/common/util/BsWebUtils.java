package com.hb.bsmanage.web.common.util;

import com.hb.bsmanage.web.common.constans.Consts;
import com.hb.bsmanage.web.dao.po.SysMerchantPO;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.StrUtils;
import com.hb.unic.util.util.UuidUtils;

/**
 * 工具类
 *
 * @version v0.1, 2020/9/17 13:35, create by huangbiao.
 */
public class BsWebUtils {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(BsWebUtils.class);

    /**
     * 获取当前Path
     *
     * @param pathOfParent 父级的level
     * @param idOfParent   父级的id
     * @return level
     */
    public static String getCurrentPath(Object pathOfParent, Object idOfParent) {
        return StrUtils.joint(pathOfParent, Consts.DOT, idOfParent);
    }

    /**
     * 获取子级Path的前缀
     *
     * @param pathOfCurrent 当前的Path
     * @return level
     */
    public static String getSubPathPrefix(Object pathOfCurrent) {
        return StrUtils.joint(pathOfCurrent, Consts.DOT);
    }

    /**
     * 获取子级商户Path的where条件
     *
     * @param where               where条件
     * @param currentUserMerchant 当前的商户
     * @return Where
     */
    public static Where getSubPathWhere(Where where, SysMerchantPO currentUserMerchant) {
        where
                .and()
                .leftBracket()
                .add(QueryType.EQUAL, "merchant_id", currentUserMerchant.getMerchantId())
                .or()
                .add(QueryType.LIKE, "path", getSubPathPrefix(currentUserMerchant.getPath()))
                .rightBracket();
        return where;
    }

    /**
     * 创建token
     *
     * @return token
     */
    public static String createToken() {
        return UuidUtils.uuidShort();
    }

}

    