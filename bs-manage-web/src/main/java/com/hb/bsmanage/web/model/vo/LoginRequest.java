package com.hb.bsmanage.web.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 登陆接口入参
 *
 * @version v0.1, 2020/7/27 21:54, create by huangbiao.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest implements Serializable {

    // 序列化ID
    private static final long serialVersionUID = 4155694160627747205L;
    // 用户名或者手机号
    private String usernameOrMobile;
    // 密码
    private String password;

}
