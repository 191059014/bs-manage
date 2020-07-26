package com.hb.bsmanage.model.enums;

/**
 * 常量类
 *
 * @version v0.1, 2020/7/24 13:51, create by huangbiao.
 */
public enum RecordStatusEnum {

    VALID(1, "有效"),
    INVALID(0, "无效");

    /**
     * 值
     */
    private Integer value;
    /**
     * 描述
     */
    private String desc;

    RecordStatusEnum(Integer value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public Integer getValue() {
        return value;
    }

    public String getDesc() {
        return desc;
    }

}
