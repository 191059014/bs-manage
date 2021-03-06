package com.hb.bsmanage.web.dao.po;

import com.hb.bsmanage.web.dao.po.base.impl.AbstractComplexPO;
import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Table;
import com.hb.unic.util.helper.ToStringHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 角色表
 *
 * @version v0.1, 2020/7/29 11:05, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_role")
public class SysRolePO extends AbstractComplexPO {
    // 序列化ID
    private static final long serialVersionUID = 8971646467070992455L;
    // 角色ID
    @Column(value = "role_id")
    private String roleId;
    // 角色名称
    @Column("role_name")
    private String roleName;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}

    