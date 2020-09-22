package com.hb.bsmanage.web.service.impl;

import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.dao.po.base.impl.AbstractBasePO;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 用户service实现类
 *
 * @version v0.1, 2020/7/24 15:00, create by huangbiao.
 */
@Service
public class SysUserServiceImpl extends DmlMapperImpl<SysUserPO, Integer, String> implements ISysUserService {

    @Override
    @InOutLog("通过用户ID或者手机号查询用户")
    public SysUserPO findByUsernameOrMobile(String usernameOrMobile) {
        Where where = Where.build()
                .and()
                .leftBracket()
                .add(QueryType.EQUAL, "user_name", usernameOrMobile)
                .or()
                .add(QueryType.EQUAL, "mobile", usernameOrMobile)
                .rightBracket();
        return selectOne(where);
    }

    @Override
    @InOutLog("通过用户ID集合查询用户list")
    public List<SysUserPO> getUserListByUserIdSet(Set<String> userIdSet) {
        Where where = Where.build()
                .and()
                .add(QueryType.IN, "user_id", userIdSet);
        return selectList(where);
    }

    @Override
    @InOutLog("通过用户ID集合查询用户map")
    public Map<String, SysUserPO> getUserMapByUserIdSet(Set<String> userIdSet) {
        List<SysUserPO> list = getUserListByUserIdSet(userIdSet);
        return list == null ? null : list.stream().collect(Collectors.toMap(SysUserPO::getUserId, v -> v, (k1, k2) -> k2));
    }

    @Override
    public <T extends AbstractBasePO> void formatCreateByAndUpdateBy(List<T> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        Set<String> userIdSet = new HashSet<>();
        list.forEach(abstractBasePO -> {
            userIdSet.add(abstractBasePO.getCreateBy());
            userIdSet.add(abstractBasePO.getUpdateBy());
        });
        if (CollectionUtils.isNotEmpty(userIdSet)) {
            Map<String, SysUserPO> userMap = getUserMapByUserIdSet(userIdSet);
            list.forEach(roleDO -> {
                SysUserPO createBy = userMap.get(roleDO.getCreateBy());
                roleDO.setCreateBy(createBy == null ? null : createBy.getUserName());
                SysUserPO updateBy = userMap.get(roleDO.getUpdateBy());
                roleDO.setUpdateBy(updateBy == null ? null : updateBy.getUserName());
            });
        }
    }

}

    