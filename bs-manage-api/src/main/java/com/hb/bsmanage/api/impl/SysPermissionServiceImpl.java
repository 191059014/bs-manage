package com.hb.bsmanage.api.impl;

import com.hb.bsmanage.api.ISysPermissionService;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 权限service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysPermissionServiceImpl extends DmlMapperImpl<SysPermissionDO, Integer, String> implements ISysPermissionService {


    @Override
    @InOutLog("通过权限id集合查询权限列表")
    public List<SysPermissionDO> getPermissionListByPermissionIdSet(Set<String> permissionIdSet) {
        return selectList(Where.build().and().add(QueryType.IN, "permission_id", permissionIdSet));
    }
}

    