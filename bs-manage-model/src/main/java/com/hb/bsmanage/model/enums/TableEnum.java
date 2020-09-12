package com.hb.bsmanage.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TableEnum {

    USER_ID("u", "用户ID前缀"),
    ROLE_ID("r", "角色ID前缀"),
    ACCESS_ID("a", "资源ID前缀"),
    ;

    private String idPrefix;
    private String desc;

}