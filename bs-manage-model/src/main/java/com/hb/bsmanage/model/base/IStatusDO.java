package com.hb.bsmanage.model.base;

/**
 * 数据记录状态
 *
 * @version v0.1, 2020/9/16 10:28, create by huangbiao.
 */
public interface IStatusDO extends ITimestampDO{

    /**
     * 获取数据记录状态
     */
    Integer getRecordStatus();

    /**
     * 设置数据记录状态
     */
    void setRecordStatus(Integer recordStatus);

}

    