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

    /**
     * 最基本的三个状态：成功，异常，失败
     */
    SUCCESS("BMW_000", "成功"),
    PARAM_ILLEGAL("BMW_001", "参数非法"),
    FAIL("BMW_499", "失败"),
    ERROR("BMW_500", "系统异常，请稍后再试"),
    /**
     * 第一个001代表“系统级别”的错误
     */
    ACCESS_DENY("BMW_SYS_P_001", "禁止访问"),
    NULL_TOKEN("BMW_SYS_P_002", "token为空"),
    ILLEGAL_TOKEN("BMW_SYS_P_003", "非法的token"),
    EXPIRE_TOKEN("BMW_SYS_P_004", "过期的token"),
    USERNAME_EMPTY("BMW_SYS_P_005", "用户名为空"),
    PWD_EMPTY("BMW_SYS_P_006", "密码为空"),
    PWD_ERROR("BMW_SYS_P_007", "密码错误"),

    ;

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
