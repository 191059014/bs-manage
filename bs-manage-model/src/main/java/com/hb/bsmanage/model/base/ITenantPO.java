package com.hb.bsmanage.model.base;

/**
 * 多租户信息
 *
 * @version v0.1, 2020/9/16 9:44, create by huangbiao.
 */
public interface ITenantPO extends ITimestampPO {

    /**
     * 获取多租户ID
     */
    String getTenantId();

    /**
     * 设置多租户ID
     */
    void setTenantId(String tenantId);

}

    