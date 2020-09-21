package com.hb.bsmanage.model.po;

import com.hb.bsmanage.model.base.impl.CommonPO;
import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Table;
import com.hb.unic.util.helper.ToStringHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户角色表
 *
 * @version v0.1, 2020/7/29 11:10, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_role_permission")
public class SysRolePermissionPO extends CommonPO {
    // 序列化ID
    private static final long serialVersionUID = 3873773717231088322L;
    // 用户ID
    @Column("role_id")
    private String roleId;
    // 权限ID
    @Column("permission_id")
    private String permissionId;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}

    