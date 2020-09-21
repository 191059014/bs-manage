package com.hb.bsmanage.model.po;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hb.bsmanage.model.base.impl.CommonPO;
import com.hb.mybatis.annotation.Column;
import com.hb.mybatis.annotation.Table;
import com.hb.unic.util.helper.ToStringHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商户表
 *
 * @version v0.1, 2020/7/24 13:51, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table("sys_merchant")
public class SysMerchantPO extends CommonPO {

    // serialVersionUID
    private static final long serialVersionUID = 5538623345484936541L;

    // 商户标识
    @Column(value = "merchant_id", isBk = true)
    private String merchantId;

    // 商户名
    @Column("merchant_name")
    private String merchantName;

    @Override
    public String toString() {
        return ToStringHelper.printNoNull(this);
    }
}

    