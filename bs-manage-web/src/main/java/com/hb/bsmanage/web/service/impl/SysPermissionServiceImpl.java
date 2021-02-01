package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.dao.base.impl.BaseDaoImpl;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.service.ISysPermissionService;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.toolkit.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 权限service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysPermissionServiceImpl extends BaseDaoImpl<SysPermissionPO> implements ISysPermissionService {


    @Override
    @InOutLog("通过权限id集合查询权限列表")
    public List<SysPermissionPO> getPermissionListByPermissionIdSet(Set<String> permissionIdSet) {
        if (CollectionUtils.isEmpty(permissionIdSet)) {
            return new ArrayList<>();
        }
        return selectList(Where.build().andCondition(QueryType.IN, "permission_id", permissionIdSet));
    }

    @Override
    @InOutLog("获取商户下所有权限集合")
    public Set<String> getPermissionIdSetByMerchantIdSet(Set<String> merchantIdSet) {
        if (CollectionUtils.isEmpty(merchantIdSet)) {
            return new HashSet<>();
        }
        List<SysPermissionPO> sysPermissionList = selectList(Where.build().andCondition(QueryType.IN, "tenant_id", merchantIdSet));
        return sysPermissionList.stream().map(SysPermissionPO::getPermissionId).collect(Collectors.toSet());
    }
}

    