package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.ISysUserService;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.enums.TableEnum;
import com.hb.bsmanage.web.common.BsWebUtils;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.util.easybuild.MapBuilder;
import com.hb.unic.util.util.KeyUtils;
import com.hb.unic.util.util.Pagination;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
     * 条件分页查询
     *
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/queryPages")
    public Result<Object> findPages(@RequestBody SysUserDO user,
                                    @RequestParam("pageNum") Integer pageNum,
                                    @RequestParam("pageSize") Integer pageSize) {
        Where where = Where.build();
        if (StringUtils.isNotBlank(user.getUserId())) {
            where.and().add(QueryType.EQUAL, "user_id", user.getUserId());
        }
        if (StringUtils.isNotBlank(user.getUserName())) {
            where.and().add(QueryType.LIKE, "user_name", user.getUserName());
        }
        if (StringUtils.isNotBlank(user.getMobile())) {
            where.and().add(QueryType.LIKE, "mobile", user.getMobile());
        }
        SysUserDO currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getParentId() != null) {
            // 非最高系统管理员，只能查询下级
            where.and().add(QueryType.LIKE, "parent_id_path", BsWebUtils.getParentIdPath(currentUser.getParentIdPath(), currentUser.getId()));
        }
        Pagination<SysUserDO> pageResult = iSysUserService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);
        return Result.of(ResponseEnum.SUCCESS, pageResult);
    }

    /**
     * 添加用户
     *
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysUserDO user) {
        if (StringUtils.isAnyBlank(user.getUserName(), user.getMobile(), user.getPassword())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        user.setUserId(KeyUtils.getUniqueKey(TableEnum.USER_ID.getIdPrefix()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        SysUserDO currentUser = SecurityUtils.getCurrentUser();
        user.setParentIdPath(BsWebUtils.getParentIdPath(currentUser.getParentIdPath(), currentUser.getId()));
        user.setParentId(currentUser.getUserId());
        user.setTenantId(currentUser.getTenantId());
        user.setCreateBy(currentUser.getUserId());
        user.setUpdateBy(currentUser.getUserId());
        int addRows = iSysUserService.insert(user);
        return Result.of(ResponseEnum.SUCCESS, addRows);
    }

    /**
     * 修改用户
     *
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/update")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result update(@RequestBody SysUserDO user, @RequestParam("userId") String userId) {
        user.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysUserService.updateByBk(userId, user);
        if (updateRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result delete(@RequestParam("userId") String userId) {
        int deleteRows = iSysUserService.logicDeleteByBk(userId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        if (deleteRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

}

    