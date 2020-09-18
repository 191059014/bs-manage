package com.hb.bsmanage.api.service;

import com.hb.bsmanage.model.dobj.SysMerchantDO;
import com.hb.mybatis.base.IDmlMapper;

import java.util.List;
import java.util.Set;

/**
 * 商户service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysMerchantService extends IDmlMapper<SysMerchantDO, Integer, String> {

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
    List<SysMerchantDO> getCurrentSubMerchantList(String currentUserTenantId);

}
