package com.hb.bsmanage.web.model.dto;

import com.hb.unic.util.helper.ToStringHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 * @author guoll
 * @date 2020/9/12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElementUIMenu implements Serializable {
    // serialVersionUID
    private static final long serialVersionUID = -8390774202795927073L;
    // 页面索引
    private String index;
    // 页面名称
    private String name;
    // 图标
    private String icon;
    // 页面链接
    private String url;
    // 针对页面类型，页面是否保持存活
    private String keepAlive;
    // 父级页面索引
    private String parentIndex;
    // 下级页面集合
    private List<ElementUIMenu> children;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}
