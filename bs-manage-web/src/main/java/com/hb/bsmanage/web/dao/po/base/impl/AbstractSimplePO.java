package com.hb.bsmanage.web.dao.po.base.impl;

import com.hb.bsmanage.web.dao.po.base.IStatusPO;
import com.hb.mybatis.annotation.Column;
import com.hb.unic.util.helper.ToStringHelper;

/**
 * 最简单的DO
 *
 * @version v0.1, 2020/9/16 10:27, create by huangbiao.
 */
public abstract class AbstractSimplePO extends AbstractBasePO implements IStatusPO {

    // serialVersionUID
    private static final long serialVersionUID = -6060485532902309L;

    // 物理主键
    @Column(isPk = true)
    private Integer id;

    // 记录状态
    @Column("record_status")
    private Integer recordStatus;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getRecordStatus() {
        return recordStatus;
    }

    @Override
    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }

}

    