package com.hb.bsmanage.model.dobj;

import com.hb.bsmanage.model.base.FixedEntity;
import com.hb.mybatis.annotation.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户表
 *
 * @version v0.1, 2020/7/24 13:51, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_user")
public class SysUserDO extends FixedEntity {
    // serialVersionUID
    private static final long serialVersionUID = -2355680884700265496L;
    // 用户标识
    private String userId;
    // 用户名
    private String userName;
    // 密码
    private String password;
    // 手机号
    private String mobile;
}

    