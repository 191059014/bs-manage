package com.hb.bsmanage.model.base;

import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Id;

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
    @Id
    private Integer id;
    // 创建时间
    @Column("create_time")
    private Date createTime;
    // 创建人
    @Column("create_user_id")
    private String createUserId;
    // 更新时间
    @Column("update_time")
    private Date updateTime;
    // 更新人
    @Column("update_user_id")
    private String updateUserId;
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

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
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
        return "{"
                + "\"id\":"
                + id
                + ",\"createTime\":\""
                + createTime + '\"'
                + ",\"createUserId\":\""
                + createUserId + '\"'
                + ",\"updateTime\":\""
                + updateTime + '\"'
                + ",\"updateUserId\":\""
                + updateUserId + '\"'
                + ",\"recordStatus\":"
                + recordStatus
                + ",\"parentId\":"
                + parentId
                + ",\"tenantId\":"
                + tenantId
                + "}";
    }
}

    