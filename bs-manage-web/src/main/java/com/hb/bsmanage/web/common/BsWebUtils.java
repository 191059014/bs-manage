package com.hb.bsmanage.web.common;

import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.StrUtils;

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
     * 获取parentIdPath
     *
     * @param parentIdPathOfParent 父级的parentIdPath
     * @param idOfParent           父级的id
     * @return parentIdPath
     */
    public static String getParentIdPath(Object parentIdPathOfParent, Object idOfParent) {
        return StrUtils.joint(parentIdPathOfParent, Consts.DOT, idOfParent);
    }

}

    