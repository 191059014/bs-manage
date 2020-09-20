package com.hb.bsmanage.model.response;

import com.hb.bsmanage.model.common.ToStringHelper;
import com.hb.bsmanage.model.model.Menu;
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
public class MenuDataResponse implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -1128889147856096856L;

    //  菜单列表
    private List<Menu> menuDatas;

    @Override
    public String toString() {
        return ToStringHelper.printJsonNoNull(this);
    }
}
