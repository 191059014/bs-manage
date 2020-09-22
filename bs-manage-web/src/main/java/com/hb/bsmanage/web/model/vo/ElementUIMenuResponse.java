package com.hb.bsmanage.web.model.vo;

import com.hb.bsmanage.web.model.dto.ElementUIMenu;
import com.hb.unic.util.helper.ToStringHelper;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单
 *
 * @author guoll
 * @date 2020/9/12
 */
@Data
public class ElementUIMenuResponse implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -1128889147856096856L;

    //  菜单列表
    private List<ElementUIMenu> menuDatas;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}
