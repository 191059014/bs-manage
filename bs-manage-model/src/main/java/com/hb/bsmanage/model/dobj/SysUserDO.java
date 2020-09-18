package com.hb.bsmanage.model.dobj;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.bsmanage.model.base.impl.CommonDO;
import com.hb.bsmanage.model.common.ToStringHelper;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table("sys_user")
public class SysUserDO extends CommonDO {

    // serialVersionUID
    private static final long serialVersionUID = -2355680884700265496L;

    // 用户标识
    @Column(value = "user_id", isBk = true)
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

    @Override
    public String toString() {
        return ToStringHelper.printJsonNoNull(this);
    }
}

    