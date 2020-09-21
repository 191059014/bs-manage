package com.hb.bsmanage.api.service.impl;

import com.hb.bsmanage.api.service.ISysRoleService;
import com.hb.bsmanage.model.po.SysRolePO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 角色service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysRoleServiceImpl extends DmlMapperImpl<SysRolePO, Integer, String> implements ISysRoleService {

    @Override
    @InOutLog("通过角色ID集合查询角色信息集合")
    public List<SysRolePO> getRoleListByRoleIdSet(Set<String> roleIdSet) {
        return selectList(Where.build().and().add(QueryType.IN, "role_id", roleIdSet));
    }

}

    