package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.dao.base.impl.BaseDaoImpl;
import com.hb.bsmanage.web.common.util.BsWebUtils;
import com.hb.bsmanage.web.dao.po.SysMerchantPO;
import com.hb.bsmanage.web.service.ISysMerchantService;
import com.hb.mybatis.toolkit.Where;
import com.hb.unic.base.util.LogHelper;
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
public class SysMerchantServiceImpl extends BaseDaoImpl<SysMerchantPO> implements ISysMerchantService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysMerchantServiceImpl.class);

    @Override
    public Set<String> getCurrentSubMerchantIdSet(String currentUserTenantId) {
        String baseLog = LogHelper.getBaseLog("获取当前用户下所有下级商户ID集合");
        LOGGER.info("{}入参={}", baseLog, currentUserTenantId);
        List<SysMerchantPO> list = getCurrentSubMerchantList(currentUserTenantId);
        Set<String> merchantIdSet = new HashSet<>();
        merchantIdSet.add(currentUserTenantId);
        if (CollectionUtils.isNotEmpty(list)) {
            merchantIdSet.addAll(list.stream().map(SysMerchantPO::getMerchantId).collect(Collectors.toSet()));
        }
        LOGGER.info("{}出参={}", baseLog, merchantIdSet);
        return merchantIdSet;
    }

    @Override
    public List<SysMerchantPO> getCurrentSubMerchantList(String currentUserTenantId) {
        String baseLog = LogHelper.getBaseLog("获取当前用户所有下级商户集合");
        LOGGER.info("{}入参={}", baseLog, currentUserTenantId);
        SysMerchantPO currentUserMerchant = selectOne(Where.build().and().equal("merchant_id", currentUserTenantId));
        Where where = BsWebUtils.getSubPathWhere(Where.build(), currentUserMerchant);
        List<SysMerchantPO> subMerchantList = selectList(where, "create_time desc");
        LOGGER.info("{}出参={}", baseLog, subMerchantList);
        return subMerchantList;
    }
}
