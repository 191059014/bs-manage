package com.hb.bsmanage.web.dao.base.impl;

import com.hb.bsmanage.web.dao.base.IBaseDao;
import com.hb.mybatis.core.impl.DmlMapperImpl;
import com.hb.mybatis.toolkit.Where;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 基础数据库操作类实现类
 *
 * @version v0.1, 2021/2/1 13:42, create by huangbiao.
 */
public class BaseDaoImpl<T> extends DmlMapperImpl<T> implements IBaseDao<T> {

    @Override
    public List<T> selectList(String resultColumns, Where where, String sort, Integer startRow, Integer pageSize) {
        where.and().equal(RECORD_STATUS_COLUMN_NAME, RECORD_STATUS_VALID);
        return super.selectList(resultColumns, where, sort, startRow, pageSize);
    }

    @Override
    public int updateByMap(Where where, Map<String, Object> propertyMap) {
        where.and().equal(RECORD_STATUS_COLUMN_NAME, RECORD_STATUS_VALID);
        return super.updateByMap(where, propertyMap);
    }

    @Override
    public int delete(Where where) {
        return this.logicDelete(where, new HashMap<>());
    }

    @Override
    public int logicDelete(Where where, Map<String, Object> propertyMap) {
        where.and().equal(RECORD_STATUS_COLUMN_NAME, RECORD_STATUS_VALID);
        propertyMap.put(RECORD_STATUS_PROPERTY_NAME, RECORD_STATUS_INVALID);
        return super.updateByMap(where, propertyMap);
    }

}
