package com.hb.bsmanage.api.service.impl;

import com.hb.bsmanage.api.common.BsApiUtils;
import com.hb.bsmanage.api.service.ISysMerchantService;
import com.hb.bsmanage.model.dobj.SysMerchantDO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.helper.Where;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 商户service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysMerchantServiceImpl extends DmlMapperImpl<SysMerchantDO, Integer, String> implements ISysMerchantService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysMerchantServiceImpl.class);

    @Override
    public Set<String> getCurrentSubMerchantIdSet(String currentUserTenantId) {
        List<SysMerchantDO> list = getCurrentSubMerchantList(currentUserTenantId);
        Set<String> merchantIdSet = new HashSet<>();
        merchantIdSet.add(currentUserTenantId);
        if (CollectionUtils.isNotEmpty(list)) {
            merchantIdSet.addAll(list.stream().map(SysMerchantDO::getMerchantId).collect(Collectors.toSet()));
        }
        return merchantIdSet;
    }

    @Override
    public List<SysMerchantDO> getCurrentSubMerchantList(String currentUserTenantId) {
        SysMerchantDO currentUserMerchant = selectByBk(currentUserTenantId);
        Where where = BsApiUtils.getSubLevelWhere(Where.build(), currentUserMerchant);
        return selectList(where, "create_time desc");
    }
}

    