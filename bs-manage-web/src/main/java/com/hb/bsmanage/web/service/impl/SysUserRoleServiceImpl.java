package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.dao.base.impl.BaseDaoImpl;
import com.hb.bsmanage.web.dao.po.SysUserRolePO;
import com.hb.bsmanage.web.service.ISysUserRoleService;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.toolkit.Where;
import com.hb.unic.base.annotation.InOutLog;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户角色关系service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserRoleServiceImpl extends BaseDaoImpl<SysUserRolePO> implements ISysUserRoleService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Override
    @InOutLog("通过用户ID查询角色ID集合")
    public Set<String> getRoleIdSetByUserId(String userId) {
        if (StringUtils.isBlank(userId)) {
            return new HashSet<>();
        }
        // 查询用户角色关联信息
        List<SysUserRolePO> userRoleList = selectList(Where.build().andCondition(QueryType.EQUAL, "user_id", userId));
        if (CollectionUtils.isEmpty(userRoleList)) {
            return new HashSet<>();
        }
        return userRoleList.stream().map(SysUserRolePO::getRoleId).collect(Collectors.toSet());
    }
}
