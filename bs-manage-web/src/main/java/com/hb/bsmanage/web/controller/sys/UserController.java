package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.enums.TableEnum;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.util.KeyUtils;
import com.hb.unic.util.util.Pagination;
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
@RequestMapping("bs/auth/user")
public class UserController extends BaseController {

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
    public Result<Integer> addOne(@RequestBody SysUserDO userDO) {
        userDO.setUserId(KeyUtils.getUniqueKey(TableEnum.USER_ID.getIdPrefix()));
        int addRows = iSysUserService.insert(userDO);
        return Result.of(ResponseEnum.SUCCESS, addRows);
    }

    /**
     * 查询集合
     *
     * @param userDO 用户信息
     * @return 结果
     */
    @PostMapping("/findPages")
    public Result<Object> findPages(@RequestBody SysUserDO userDO) {
        Pagination<SysUserDO> pageResult = iSysUserService.selectPages(null, "create_time desc", 0, 10);
        return Result.of(ResponseEnum.SUCCESS, pageResult);
    }

}

    