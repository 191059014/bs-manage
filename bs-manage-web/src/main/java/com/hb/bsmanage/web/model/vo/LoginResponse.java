package com.hb.bsmanage.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登陆接口出参
 *
 * @version v0.1, 2020/7/27 21:54, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse implements Serializable {

    // 序列化ID
    private static final long serialVersionUID = 4155694160627747205L;
    // 用户名
    private String username;
    // jwt令牌
    private String jwt;
}
