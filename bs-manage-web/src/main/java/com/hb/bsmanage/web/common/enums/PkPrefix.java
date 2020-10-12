package com.hb.bsmanage.web.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PkPrefix {

    USER_ID("u", "用户ID前缀"),
    ROLE_ID("r", "角色ID前缀"),
    PERMISSION_ID("p", "资源ID前缀"),
    MERCHANT_ID("m", "商户ID前缀"),;

    private String value;
    private String desc;

}
