package com.hb.bsmanage.model.base;

import java.io.Serializable;

/**
 * 常用固定属性
 *
 * @version v0.1, 2020/7/24 13:53, create by huangbiao.
 */
public class FixedEntity implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 2134467222395310313L;
    // 物理主键
    private Integer id;
    // 创建时间
    private Integer createTime;
    // 更新时间
    private Integer updateTime;
    // 记录状态
    private Integer recordStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRecordStatus() {
        return recordStatus;
    }

    public void setRecordStatus(Integer recordStatus) {
        this.recordStatus = recordStatus;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":"
                + id
                + ",\"createTime\":"
                + createTime
                + ",\"updateTime\":"
                + updateTime
                + ",\"recordStatus\":"
                + recordStatus
                + "}";
    }
}

    