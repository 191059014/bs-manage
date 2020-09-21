package com.hb.bsmanage.api.service.impl;

import com.hb.bsmanage.api.service.ISysPermissionService;
import com.hb.bsmanage.model.po.SysPermissionPO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysPermissionServiceImpl extends DmlMapperImpl<SysPermissionPO, Integer, String> implements ISysPermissionService {


    @Override
    @InOutLog("通过权限id集合查询权限列表")
    public List<SysPermissionPO> getPermissionListByPermissionIdSet(Set<String> permissionIdSet) {
        return selectList(Where.build().andAdd(QueryType.IN, "permission_id", permissionIdSet));
    }

    @Override
    public Set<String> getPermissionSetByMerchantId(String merchantId) {
        List<SysPermissionPO> sysPermissionList = selectList(Where.build().andAdd(QueryType.EQUAL, "tenant_id", merchantId));
        if (CollectionUtils.isEmpty(sysPermissionList)) {
            return null;
        }
        return sysPermissionList.stream().map(SysPermissionPO::getPermissionId).collect(Collectors.toSet());
    }
}

    