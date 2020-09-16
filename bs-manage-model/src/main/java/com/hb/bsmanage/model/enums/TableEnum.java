package com.hb.bsmanage.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TableEnum {

    USER_ID("u", "用户ID前缀"),
    ROLE_ID("r", "角色ID前缀"),
    PERMISSION_ID("p", "资源ID前缀"),
    MERCHANT_ID("m", "商户ID前缀"),;

    private String idPrefix;
    private String desc;

}
