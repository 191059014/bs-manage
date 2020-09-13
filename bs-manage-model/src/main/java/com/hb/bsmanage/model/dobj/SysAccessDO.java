package com.hb.bsmanage.model.dobj;

import com.hb.bsmanage.model.base.FixedDO;
import com.hb.bsmanage.model.common.ToStringHelper;
import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Table;
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
@Table("sys_access")
public class SysAccessDO extends FixedDO {

    // 序列化ID
    private static final long serialVersionUID = 4702444967524003359L;
    // 权限ID
    @Column(value = "access_id", isBk = true)
    private String accessId;
    // 权限名称
    @Column("access_name")
    private String accessName;
    // 权限类型
    @Column("access_type")
    private String accessType;
    // 权限值
    @Column("access_value")
    private String accessValue;
    // 图标
    private String icon;
    // 链接
    private String url;

    @Override
    public String toString() {
        return ToStringHelper.printJsonNoNull(this);
    }
}
