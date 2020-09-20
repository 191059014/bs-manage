package com.hb.bsmanage.model.base.impl;

import com.hb.bsmanage.model.base.IRelationDO;
import com.hb.bsmanage.model.base.IStatusDO;
import com.hb.bsmanage.model.base.ITenantDO;
import com.hb.mybatis.annotation.Column;
import com.hb.unic.util.helper.ToStringHelper;

/**
 * 通常的DO
 *
 * @version v0.1, 2020/9/16 10:27, create by huangbiao.
 */
public abstract class CommonDO extends AbstractBaseDO implements IStatusDO, IRelationDO, ITenantDO {

    // serialVersionUID
    private static final long serialVersionUID = -6060485532902309L;

    // 物理主键
    @Column(isPk = true)
    private Integer id;

    // 记录状态
    @Column("record_status")
    private Integer recordStatus;

    // 父级ID
    @Column("parent_id")
    private String parentId;

    // 多租户ID
    @Column("tenant_id")
    private String tenantId;

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
    public String getParentId() {
        return parentId;
    }

    @Override
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }

}

    