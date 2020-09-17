package com.hb.bsmanage.api.impl;

import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.mybatis.base.DmlMapperImpl;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
import org.springframework.stereotype.Service;

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
public class SysUserServiceImpl extends DmlMapperImpl<SysUserDO, Integer, String> implements ISysUserService {

    @Override
    @InOutLog("通过用户ID或者手机号查询用户")
    public SysUserDO findByUsernameOrMobile(String usernameOrMobile) {
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
    @InOutLog("通过用户ID集合查询用户信息")
    public List<SysUserDO> getUserListByUserIdSet(Set<String> userIdSet) {
        Where where = Where.build()
                .and()
                .add(QueryType.IN, "user_id", userIdSet);
        return selectList(where);
    }

    @Override
    public Map<String, SysUserDO> getUserMapByUserIdSet(Set<String> userIdSet) {
        List<SysUserDO> list = getUserListByUserIdSet(userIdSet);
        return list == null ? null : list.stream().collect(Collectors.toMap(SysUserDO::getUserId, v -> v, (k1, k2) -> k2));
    }

}

    