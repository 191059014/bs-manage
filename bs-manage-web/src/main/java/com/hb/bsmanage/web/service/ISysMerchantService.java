package com.hb.bsmanage.web.service;

import com.hb.bsmanage.web.dao.base.IBaseDao;
import com.hb.bsmanage.web.dao.po.SysMerchantPO;

import java.util.List;
import java.util.Set;

/**
 * 商户service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysMerchantService extends IBaseDao<SysMerchantPO> {

    /**
     * 获取当前商户所有下级的商户ID集合
     *
     * @return 商户ID集合
     */
    Set<String> getCurrentSubMerchantIdSet(String currentUserTenantId);

    /**
     * 获取当前商户所有下级的商户集合
     *
     * @return 商户集合
     */
    List<SysMerchantPO> getCurrentSubMerchantList(String currentUserTenantId);

}
