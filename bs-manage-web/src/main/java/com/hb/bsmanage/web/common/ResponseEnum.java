package com.hb.bsmanage.web.common;

/**
 * 响应枚举
 * 应用标识 + 功能域 + 错误类型 + 错误编码
 * （bmw + 001 + P + 001）
 * P-参数错误
 * B-业务错误
 * N-网络错误
 * D-数据库错误
 * F-文件IO错误
 * O-其他错误
 *
 * @version v0.1, 2020/7/24 15:35, create by huangbiao.
 */
public enum ResponseEnum {

    SUCCESS("BMW_00000", "成功"),
    ERROR("BMW_99999", "系统异常，请稍后再试"),;

    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String msg;

    ResponseEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
