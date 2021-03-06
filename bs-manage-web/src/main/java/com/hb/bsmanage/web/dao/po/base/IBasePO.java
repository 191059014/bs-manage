package com.hb.bsmanage.web.dao.po.base;

import java.io.Serializable;

/**
 * 基础实体
 *
 * @version v0.1, 2020/9/16 9:36, create by huangbiao.
 */
public interface IBasePO extends Serializable, Cloneable {

    /**
     * 获取主键
     */
    Integer getId();

    /**
     * 设置主键
     */
    void setId(Integer id);

}

    