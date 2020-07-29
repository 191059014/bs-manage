package com.hb.bsmanage.model.dobj;

import com.hb.bsmanage.model.base.FixedDO;
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
    private String accessId;
    // 权限名称
    private String accessName;
    // 权限类型
    private String accessType;
    // 权限值
    private String accessValue;
    // 图标
    private String icon;
    // 链接
    private String url;
}
