package com.hb.bsmanage.model.base;

import com.hb.bsmanage.model.common.ToStringHelper;
import com.hb.mybatis.annotation.Column;

import java.io.Serializable;
import java.util.Date;

/**
 * 常用固定属性
 *
 * @version v0.1, 2020/7/24 13:53, create by huangbiao.
 */
public class FixedDO implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 2134467222395310313L;
    // 物理主键
    @Column(isPk = true)
    private Integer id;
    // 创建时间
    @Column("create_time")
    private Date createTime;
    // 创建人
    @Column("create_by")
    private String createBy;
    // 更新时间
    @Column("update_time")
    private Date updateTime;
    // 更新人
    @Column("update_by")
    private String updateBy;
    // 记录状态
    @Column("record_status")
    private Integer recordStatus;
    // 父级ID
    @Column("parent_id")
    private Integer parentId;
    // 多租户ID
    @Column("tenant_id")
    private Integer tenantId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTenantId() {
        return tenantId;
    }

    public void setTenantId(Integer tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }

}

    