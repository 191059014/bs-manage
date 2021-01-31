package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.service.ISysRoleAccessService;
import com.hb.bsmanage.web.dao.po.SysRolePermissionPO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.tool.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 角色权限关系service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysRoleAccessServiceImpl extends DmlMapperImpl<SysRolePermissionPO, Integer, String> implements ISysRoleAccessService {

    @Override
    @InOutLog("通过角色ID集合查询角色权限关系集合")
    public Set<String> getPermissionIdSetByRoleIdSet(Set<String> roleIdSet) {
        if (CollectionUtils.isEmpty(roleIdSet)) {
            return new HashSet<>();
        }
        List<SysRolePermissionPO> rolePermissionList = selectList(Where.build().andCondition(QueryType.IN, "role_id", roleIdSet));
        if (CollectionUtils.isEmpty(rolePermissionList)) {
            return new HashSet<>();
        }
        return rolePermissionList.stream().map(SysRolePermissionPO::getPermissionId).collect(Collectors.toSet());
    }
}

    