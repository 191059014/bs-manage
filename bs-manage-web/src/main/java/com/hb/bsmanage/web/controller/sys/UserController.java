package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.service.ISysMerchantService;
import com.hb.bsmanage.api.service.ISysRoleService;
import com.hb.bsmanage.api.service.ISysUserRoleService;
import com.hb.bsmanage.api.service.ISysUserService;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.dobj.SysUserRoleDO;
import com.hb.bsmanage.model.enums.TableEnum;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.logger.util.LogHelper;
import com.hb.unic.util.easybuild.MapBuilder;
import com.hb.unic.util.util.KeyUtils;
import com.hb.unic.util.util.Pagination;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 商户service
     */
    @Autowired
    private ISysMerchantService iSysMerchantService;

    /**
     * 角色service
     */
    @Autowired
    private ISysRoleService iSysRoleService;

    /**
     * 用户角色关系service
     */
    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    /**
     * 条件分页查询
     *
     * @param user 用户信息
     * @return 结果
     */
    @PostMapping("/queryPages")
    public Result<Pagination<SysUserDO>> findPages(@RequestBody SysUserDO user,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        String baseLog = LogHelper.getBaseLog("分页查询用户信息");
        LOGGER.info("{}入参={}={}={}", baseLog, user, pageNum, pageSize);
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "user_id", user.getUserId());
        where.andAdd(QueryType.LIKE, "user_name", user.getUserName());
        where.andAdd(QueryType.LIKE, "mobile", user.getMobile());
        where.andAdd(QueryType.EQUAL, "tenant_id", user.getTenantId());
        SysUserDO currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getParentId() != null) {
            // 非最高系统管理员，只能查询用户所属商户，及商户下的所有下级商户的用户
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "tenant_id", merchantIdSet);
        }
        Pagination<SysUserDO> pageResult = iSysUserService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);

        List<SysUserDO> userList = pageResult.getData();
        if (CollectionUtils.isNotEmpty(userList)) {
            Set<String> userIdSet = new HashSet<>();
            userList.forEach(userDO -> {
                userIdSet.add(userDO.getCreateBy());
                userIdSet.add(userDO.getUpdateBy());
            });
            if (CollectionUtils.isNotEmpty(userIdSet)) {
                Map<String, SysUserDO> userMap = iSysUserService.getUserMapByUserIdSet(userIdSet);
                userList.forEach(userDO -> {
                    SysUserDO createBy = userMap.get(userDO.getCreateBy());
                    userDO.setCreateBy(createBy == null ? null : createBy.getUserName());
                    SysUserDO updateBy = userMap.get(userDO.getUpdateBy());
                    userDO.setUpdateBy(updateBy == null ? null : updateBy.getUserName());
                });
            }
        }
        LOGGER.info("{}出参={}", baseLog, pageResult);
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
        user.setParentId(SecurityUtils.getCurrentUserId());
        user.setTenantId(SecurityUtils.getCurrentUserTenantId());
        user.setCreateBy(SecurityUtils.getCurrentUserId());
        user.setUpdateBy(SecurityUtils.getCurrentUserId());
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

    /**
     * 获取用户的角色集合
     *
     * @return 树数据
     */
    @GetMapping("/getRolesUnderUser")
    public Result<Set<String>> getRolesUnderUser(@RequestParam("userId") String userId) {
        SysUserDO sysUserDO = iSysUserService.selectByBk(userId);
        List<SysUserRoleDO> userRoleList = iSysUserRoleService.selectList(Where.build().andAdd(QueryType.EQUAL, "user_id", sysUserDO.getUserId()));
        Set<String> roleIdSet = userRoleList.stream().map(SysUserRoleDO::getRoleId).collect(Collectors.toSet());
        return Result.of(ResponseEnum.SUCCESS, roleIdSet);
    }

    /**
     * 获取用户对应商户下所有角色集合
     *
     * @return 树数据
     */
    @GetMapping("/getRolesUnderMerchant")
    public Result<List<SysRoleDO>> getRolesUnderMerchant(@RequestParam("userId") String userId) {
        SysUserDO sysUserDO = iSysUserService.selectByBk(userId);
        return Result.of(ResponseEnum.SUCCESS, iSysRoleService.selectList(Where.build().andAdd(QueryType.EQUAL, "tenant_id", sysUserDO.getTenantId())));
    }

    /**
     * 更新用户的角色
     *
     * @param userId    用户id
     * @param roleIdSet 角色集合
     * @return 更新结果
     */
    @PostMapping("/updateUserRole")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result updateUserRole(@RequestParam("userId") String userId, @RequestBody Set<String> roleIdSet) {
        if (StringUtils.isBlank(userId) || CollectionUtils.isEmpty(roleIdSet)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        // 删除用户的角色信息
        Where deleteWhere = Where.build().andAdd(QueryType.EQUAL, "user_id", userId);
        Map<String, Object> updateMap = MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get();
        iSysUserRoleService.logicDelete(deleteWhere, updateMap);
        // 添加用户的角色信息
        roleIdSet.forEach(roleId -> {
            SysUserRoleDO insert = SysUserRoleDO.builder().userId(userId).roleId(roleId).build();
            insert.setCreateBy(SecurityUtils.getCurrentUserId());
            insert.setUpdateBy(SecurityUtils.getCurrentUserId());
            insert.setTenantId(SecurityUtils.getCurrentUserTenantId());
            iSysUserRoleService.insert(insert);
        });
        return Result.of(ResponseEnum.SUCCESS);
    }

}

    