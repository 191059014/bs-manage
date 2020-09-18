package com.hb.bsmanage.api.service.impl;

import com.hb.bsmanage.api.service.ISysRoleAccessService;
import com.hb.bsmanage.model.dobj.SysRolePermissionDO;
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
 * 角色权限关系service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysRoleAccessServiceImpl extends DmlMapperImpl<SysRolePermissionDO, Integer, String> implements ISysRoleAccessService {

    @Override
    @InOutLog("通过角色ID集合查询角色权限关系集合")
    public Set<String> getPermissionIdSetByRoleIdSet(Set<String> roleIdSet) {
        List<SysRolePermissionDO> rolePermissionList = selectList(Where.build().and().add(QueryType.IN, "role_id", roleIdSet));
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return null;
        }
        return rolePermissionList.stream().map(SysRolePermissionDO::getPermissionId).collect(Collectors.toSet());
    }
}

    