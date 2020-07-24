package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.sys.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.controller.base.AbstractController;
import com.hb.unic.base.common.ResponseData;
import com.hb.unic.base.util.ResponseUtils;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户controller
 *
 * @version v0.1, 2020/7/24 15:05, create by huangbiao.
 */
@RestController
@RequestMapping("/controller/user")
public class UserController extends AbstractController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /**
     * 用户service
     */
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 添加用户
     *
     * @param userDO 用户信息
     * @return 结果
     */
    @PostMapping("/addOne")
    public ResponseData<Integer> addOne(@RequestBody SysUserDO userDO) {
        int addRows = iSysUserService.add(userDO);
        return ResponseUtils.generateResponseData(ResponseEnum.SUCCESS, addRows);
    }

}

    