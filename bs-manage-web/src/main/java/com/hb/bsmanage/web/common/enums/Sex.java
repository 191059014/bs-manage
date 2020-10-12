package com.hb.bsmanage.web.common.enums;

/**
 * 性别
 *
 * @version v0.1, 2020/7/29 10:58, create by huangbiao.
 */
public enum Sex {

    MAN("M", "男"),
    WOMAN("F", "女");

    // 值
    private String value;
    // 名称
    private String name;

    Sex(String value, String name) {
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

    