package com.hb.bsmanage.api;

import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.mybatis.base.IDmlMapper;

/**
 * 用户service接口
 *
 * @version v0.1, 2020/7/24 14:59, create by huangbiao.
 */
public interface ISysUserService extends IDmlMapper<SysUserDO> {

    /**
     * 通过用户ID或者手机号查询
     *
     * @param usernameOrMobile 用户ID或者手机号查询
     * @return 用户信息
     */
    SysUserDO findByUsernameOrMobile(String usernameOrMobile);

}
