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
@Table("sys_resource")
public class SysResourceDO extends FixedDO {

    // 序列化ID
    private static final long serialVersionUID = 4702444967524003359L;
    // 资源ID
    private String resourceId;
    // 资源名称
    private String resourceName;
}
