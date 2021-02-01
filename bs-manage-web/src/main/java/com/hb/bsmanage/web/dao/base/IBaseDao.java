package com.hb.bsmanage.web.dao.base;

import com.hb.mybatis.core.IDmlMapper;
import com.hb.mybatis.toolkit.Where;

import java.util.Map;

/**
 * 基础数据库操作类
 *
 * @version v0.1, 2021/2/1 13:41, create by huangbiao.
 */
public interface IBaseDao<T> extends IDmlMapper<T> {

    /**
     * 逻辑删除数据库字段
     */
    public static final String RECORD_STATUS_COLUMN_NAME = "record_status";

    /**
     * 逻辑删除实体类字段
     */
    public static final String RECORD_STATUS_PROPERTY_NAME = "recordStatus";

    /**
     * 逻辑删除 - 有效
     */
    public static final Integer RECORD_STATUS_VALID = 1;

    /**
     * 逻辑删除 - 无效
     */
    public static final Integer RECORD_STATUS_INVALID = 0;

    /**
     * 逻辑删除
     * 
     * @param where
     *            where条件
     * @param propertyMap
     *            属性集合
     * @return 影响的行数
     */
    int logicDelete(Where where, Map<String, Object> propertyMap);

}
