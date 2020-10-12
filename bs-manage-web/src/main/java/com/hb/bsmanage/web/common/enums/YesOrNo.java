package com.hb.bsmanage.web.common.enums;

/**
 * 是或否
 *
 * @version v0.1, 2020/10/12 13:47, create by huangbiao.
 */
public enum YesOrNo {

    YES("Y", "是"),
    NO("N", "否");

    // 值
    private String value;
    // 名称
    private String name;

    YesOrNo(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

}

    