package com.hb.bsmanage.web.service;

import com.hb.bsmanage.web.dao.base.IBaseDao;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.dao.po.base.impl.AbstractBasePO;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 用户service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysUserService extends IBaseDao<SysUserPO> {

    /**
     * 通过用户ID或者手机号查询用户
     *
     * @param usernameOrMobile
     *            用户ID或者手机号查询
     * @return 用户信息
     */
    SysUserPO findByUsernameOrMobile(String usernameOrMobile);

    /**
     * 通过用户ID集合查询用户信息
     *
     * @param userIdSet
     *            用户ID集合
     * @return 用户信息集合
     */
    List<SysUserPO> getUserListByUserIdSet(Set<String> userIdSet);

    /**
     * 通过用户ID集合查询用户信息
     *
     * @param userIdSet
     *            用户ID集合
     * @return 用户信息集合
     */
    Map<String, SysUserPO> getUserMapByUserIdSet(Set<String> userIdSet);

    /**
     * 转换创建人和更新人为中文名字
     *
     * @param list
     *            需要被更新的集合
     */
    <T extends AbstractBasePO> void formatCreateByAndUpdateBy(List<T> list);

}
