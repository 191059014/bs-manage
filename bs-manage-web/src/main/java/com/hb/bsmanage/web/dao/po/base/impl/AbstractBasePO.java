package com.hb.bsmanage.web.dao.po.base.impl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb.bsmanage.web.dao.po.base.ITimestampPO;
import com.hb.mybatis.annotation.Column;
import com.hb.unic.util.helper.ToStringHelper;
import com.hb.unic.util.util.DateUtils;

import java.util.Date;

/**
 * 常用固定属性
 *
 * @version v0.1, 2020/7/24 13:53, create by huangbiao.
 */
public abstract class AbstractBasePO implements ITimestampPO {

    // serialVersionUID
    private static final long serialVersionUID = 2134467222395310313L;

    // 创建时间
    @Column("create_time")
    @JsonFormat(pattern = DateUtils.FORMAT_3, timezone = DateUtils.TIME_ZONE_1)
    private Date createTime;

    // 创建人
    @Column("create_by")
    private String createBy;

    // 更新时间
    @Column("update_time")
    @JsonFormat(pattern = DateUtils.FORMAT_3, timezone = DateUtils.TIME_ZONE_1)
    private Date updateTime;

    // 更新人
    @Column("update_by")
    private String updateBy;

    @Override
    public Date getCreateTime() {
        return createTime;
    }

    @Override
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String getCreateBy() {
        return createBy;
    }

    @Override
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    @Override
    public Date getUpdateTime() {
        return updateTime;
    }

    @Override
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String getUpdateBy() {
        return updateBy;
    }

    @Override
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 重置
     */
    public void resetTimestamp() {
        setCreateTime(null);
        setCreateBy(null);
        setUpdateTime(null);
        setUpdateBy(null);
    }

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }

}

    