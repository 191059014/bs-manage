package com.hb.bsmanage.web.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 修改密码请求参数
 *
 * @version v0.1, 2020/10/13 13:34, create by huangbiao.
 */
@Data
public class ModifyPasswordRequest implements Serializable {

    // serialVersionUID
    private static final long serialVersionUID = -3902630949904383103L;

    // 旧密码
    private String oldPassword;

    // 新密码
    private String newPassword;

}

    