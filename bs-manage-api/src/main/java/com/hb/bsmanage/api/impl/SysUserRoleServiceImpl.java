package com.hb.bsmanage.api.impl;

import com.hb.bsmanage.api.ISysUserRoleService;
import com.hb.bsmanage.model.dobj.SysUserRoleDO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色关系service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserRoleServiceImpl extends DmlMapperImpl<SysUserRoleDO, Integer, String> implements ISysUserRoleService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Override
    @InOutLog("通过用户ID查询角色ID集合")
    public Set<String> getRoleIdSetByUserId(String userId) {
        // 查询用户角色关联信息
        List<SysUserRoleDO> userRoleList = selectList(Where.build().add(QueryType.EQUAL, "user_id", userId));
        if (CollectionUtils.isEmpty(userRoleList)) {
            return null;
        }
        return userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
    }
}

    