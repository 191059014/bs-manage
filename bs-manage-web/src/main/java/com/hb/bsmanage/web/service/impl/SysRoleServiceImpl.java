package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.service.ISysRoleService;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.tool.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (CollectionUtils.isEmpty(roleIdSet)) {
            return new ArrayList<>();
        }
        return selectList(Where.build().andCondition(QueryType.IN, "role_id", roleIdSet));
    }

}

    