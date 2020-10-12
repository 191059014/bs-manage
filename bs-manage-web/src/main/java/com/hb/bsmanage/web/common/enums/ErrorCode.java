package com.hb.bsmanage.web.common.enums;

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
public enum ErrorCode {

    /**
     * 最基本的三个状态：成功，异常，失败，参数非法
     */
    SUCCESS("10000", "成功"),
    FAIL("10001", "失败"),
    PARAM_ILLEGAL("10002", "参数非法"),
    ERROR("10003", "系统异常，请稍后再试"),
    /**
     * 20100-20199代表“系统级别”的错误
     */
    ACCESS_DENY("20100", "禁止访问"),
    TOKEN_IS_EMPTY("20110", "token为空"),
    TOKEN_IS_EXPIRED("20111", "token过期"),
    USER_NOT_EXIST("20102", "用户不存在"),
    BAD_CREDENTIALS("20103", "用户名或密码错误"),

    ;

    /**
     * 响应码
     */
    private String code;
    /**
     * 响应信息
     */
    private String msg;

    ErrorCode(String code, String msg) {
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
