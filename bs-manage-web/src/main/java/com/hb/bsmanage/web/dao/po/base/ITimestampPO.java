package com.hb.bsmanage.web.dao.po.base;

import java.util.Date;

/**
 * 时间戳实体
 *
 * @version v0.1, 2020/9/16 9:35, create by huangbiao.
 */
public interface ITimestampPO extends IBasePO {

    /**
     * 获取创建时间
     */
    Date getCreateTime();

    /**
     * 设置创建时间
     */
    void setCreateTime(Date createTime);

    /**
     * 获取创建者
     */
    String getCreateBy();

    /**
     * 设置创建者
     */
    void setCreateBy(String createBy);

    /**
     * 获取更新时间
     */
    Date getUpdateTime();

    /**
     * 设置更新时间
     */
    void setUpdateTime(Date updateTime);

    /**
     * 获取更新人
     */
    String getUpdateBy();

    /**
     * 设置更新人
     */
    void setUpdateBy(String updateBy);

}

    