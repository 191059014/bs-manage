package com.hb.bsmanage.web.dao.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.bsmanage.web.dao.po.base.impl.AbstractComplexPO;
import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Table;
import com.hb.unic.util.helper.ToStringHelper;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table("sys_user")
public class SysUserPO extends AbstractComplexPO {

    // serialVersionUID
    private static final long serialVersionUID = -2355680884700265496L;

    // 用户标识
    @Column(value = "user_id")
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

    // 性别
    @Column("sex")
    private String sex;

    // 邮箱
    @Column("email")
    private String email;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}

    