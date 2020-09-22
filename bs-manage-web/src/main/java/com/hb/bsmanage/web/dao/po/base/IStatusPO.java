package com.hb.bsmanage.web.dao.po.base;

/**
 * 数据记录状态
 *
 * @version v0.1, 2020/9/16 10:28, create by huangbiao.
 */
public interface IStatusPO extends ITimestampPO {

    /**
     * 获取数据记录状态
     */
    Integer getRecordStatus();

    /**
     * 设置数据记录状态
     */
    void setRecordStatus(Integer recordStatus);

}

    