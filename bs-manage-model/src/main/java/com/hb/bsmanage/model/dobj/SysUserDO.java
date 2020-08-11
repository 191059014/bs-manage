package com.hb.bsmanage.model.dobj;

import com.hb.bsmanage.model.base.FixedDO;
import com.hb.mybatis.annotation.Column;
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
public class SysUserDO extends FixedDO {
    // serialVersionUID
    private static final long serialVersionUID = -2355680884700265496L;
    // 用户标识
    @Column("user_id")
    private String userId;
    // 用户名
    @Column("user_name")
    private String userName;
    // 密码
    @Column("password")
    private String password;
    // 手机号
    @Column("mobile")
    private String mobile;
}

    