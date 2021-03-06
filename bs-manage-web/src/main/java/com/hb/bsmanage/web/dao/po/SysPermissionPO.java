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
 * 权限实体
 *
 * @version v0.1, 2020/7/27 21:54, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("sys_permission")
public class SysPermissionPO extends AbstractComplexPO {

    // 序列化ID
    private static final long serialVersionUID = 4702444967524003359L;
    // 权限ID
    @Column(value = "permission_id")
    private String permissionId;
    // 权限名称
    @Column("permission_name")
    private String permissionName;
    // 资源类型
    @Column("resource_type")
    private String resourceType;
    // 权限值
    @Column("value")
    private String value;
    // 图标
    private String icon;
    // 链接
    private String url;
    // 缓存页面
    private String keepAlive;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}
