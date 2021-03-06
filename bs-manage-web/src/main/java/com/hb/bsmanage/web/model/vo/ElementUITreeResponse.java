package com.hb.bsmanage.web.model.vo;

import com.hb.bsmanage.web.model.dto.ElementUITree;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 树形结构响应实体
 *
 * @version v0.1, 2020/9/18 18:40, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElementUITreeResponse implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = 6066278139558557896L;

    // 树所有数据
    private List<ElementUITree> treeDataList;

}

    