package com.hb.bsmanage.web.dao.po;

import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Table;
import lombok.Data;
import java.io.Serializable;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.hb.unic.util.util.DateUtils;

/**
 * 全局配置表数据模型
 *
 * @version v0.1, 2021-02-03 11:11:47, create by Mr.Huang.
 */
@Data
@Table("global_config")
public class GlobalConfigPO implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 4768855618084875458L;

    /**
     * 主键
     */
    @Column(value = "id", isPk = true)
    private Integer id;

    /**
     * 配置分组的ID
     */
    @Column(value = "cfg_group_id")
    private String cfgGroupId;

    /**
     * 配置的ID
     */
    @Column(value = "cfg_id")
    private String cfgId;

    /**
     * 配置的值
     */
    @Column(value = "cfg_value")
    private String cfgValue;

    /**
     * 配置的备注
     */
    @Column(value = "cfg_remark")
    private String cfgRemark;

    /**
     * 创建人
     */
    @Column(value = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(value = "create_time")
    @JsonFormat(pattern = DateUtils.FORMAT_3, timezone = DateUtils.TIME_ZONE_1)
    private Date createTime;

    /**
     * 更新人
     */
    @Column(value = "update_by")
    private String updateBy;

    /**
     * 更新时间
     */
    @Column(value = "update_time")
    @JsonFormat(pattern = DateUtils.FORMAT_3, timezone = DateUtils.TIME_ZONE_1)
    private Date updateTime;

    /**
     * 记录有效状态
     */
    @Column(value = "record_status")
    private Integer recordStatus;

    /**
     * 多租户ID
     */
    @Column(value = "tenant_id")
    private String tenantId;

}
