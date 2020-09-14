package com.hb.bsmanage.model.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb.bsmanage.model.common.ToStringHelper;
import com.hb.mybatis.annotation.Column;
import com.hb.unic.util.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.TimeZone;

/**
 * 常用固定属性
 *
 * @version v0.1, 2020/7/24 13:53, create by huangbiao.
 */
@Setter
@Getter
public class FixedDO implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 2134467222395310313L;
    // 物理主键
    @Column(isPk = true)
    private Integer id;
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
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }

}

    