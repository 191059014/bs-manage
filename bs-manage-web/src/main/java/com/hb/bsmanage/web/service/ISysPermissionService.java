package com.hb.bsmanage.web.service;

import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.mybatis.base.IDmlMapper;

import java.util.List;
import java.util.Set;

/**
 * 权限service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysPermissionService extends IDmlMapper<SysPermissionPO, Integer, String> {

    /**
     * 通过权限id集合查询权限列表
     *
     * @param permissionIdSet 权限id集合
     * @return 权限列表
     */
    List<SysPermissionPO> getPermissionListByPermissionIdSet(Set<String> permissionIdSet);

    /**
     * 查询商户下的权限列表
     *
     * @param merchantIdSet 商户ID集合
     * @return 权限id列表
     */
    Set<String> getPermissionIdSetByMerchantIdSet(Set<String> merchantIdSet);

}
