package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.web.common.enums.ResponseEnum;
import com.hb.bsmanage.web.common.enums.TableEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.dao.po.SysUserRolePO;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.bsmanage.web.service.ISysMerchantService;
import com.hb.bsmanage.web.service.ISysRoleService;
import com.hb.bsmanage.web.service.ISysUserRoleService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.annotation.InOutLog;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Result<Pagination<SysUserPO>> findPages(@RequestBody SysUserPO user,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        String baseLog = LogHelper.getBaseLog("分页查询用户信息");
        LOGGER.info("{}入参={}={}={}", baseLog, user, pageNum, pageSize);
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "user_id", user.getUserId());
        where.andAdd(QueryType.LIKE, "user_name", user.getUserName());
        where.andAdd(QueryType.LIKE, "mobile", user.getMobile());
        where.andAdd(QueryType.EQUAL, "tenant_id", user.getTenantId());
        SysUserPO currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getParentId() != null) {
            // 非最高系统管理员，只能查询用户所属商户，及商户下的所有下级商户的用户
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "tenant_id", merchantIdSet);
        }
        Pagination<SysUserPO> pageResult = iSysUserService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);

        List<SysUserPO> userList = pageResult.getData();
        iSysUserService.formatCreateByAndUpdateBy(userList);
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
    public Result<Integer> add(@RequestBody SysUserPO user) {
        String baseLog = LogHelper.getBaseLog("添加用户");
        LOGGER.info("{}入参={}", baseLog, user);
        if (StringUtils.isAnyBlank(user.getTenantId(), user.getUserName(), user.getMobile(), user.getPassword())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        user.setUserId(KeyUtils.getUniqueKey(TableEnum.USER_ID.getIdPrefix()));
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setParentId(SecurityUtils.getCurrentUserId());
        user.setTenantId(user.getTenantId());
        user.setCreateBy(SecurityUtils.getCurrentUserId());
        user.setUpdateBy(SecurityUtils.getCurrentUserId());
        LOGGER.info("{}准备入库={}", baseLog, user);
        int addRows = iSysUserService.insert(user);
        LOGGER.info("{}出参={}", baseLog, addRows);
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
    public Result<Integer> update(@RequestBody SysUserPO user, @RequestParam("userId") String userId) {
        String baseLog = LogHelper.getBaseLog("修改用户");
        LOGGER.info("{}入参={}={}", baseLog, userId, user);
        user.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysUserService.updateByBk(userId, user);
        if (updateRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        LOGGER.info("{}出参={}={}", baseLog, updateRows);
        return Result.of(ResponseEnum.SUCCESS, updateRows);
    }

    /**
     * 删除用户
     *
     * @param userId 用户ID
     * @return 结果
     */
    @GetMapping("/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Integer> delete(@RequestParam("userId") String userId) {
        String baseLog = LogHelper.getBaseLog("删除用户");
        LOGGER.info("{}入参={}", baseLog, userId);
        int deleteRows = iSysUserService.logicDeleteByBk(userId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        if (deleteRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        LOGGER.info("{}出参={}", baseLog, deleteRows);
        return Result.of(ResponseEnum.SUCCESS, deleteRows);
    }

    /**
     * 获取用户的角色集合
     *
     * @return 树数据
     */
    @GetMapping("/getRolesUnderUser")
    @InOutLog("获取用户的角色集合")
    public Result<Set<String>> getRolesUnderUser(@RequestParam("userId") String userId) {
        SysUserPO sysUserDO = iSysUserService.selectByBk(userId);
        List<SysUserRolePO> userRoleList = iSysUserRoleService.selectList(Where.build().andAdd(QueryType.EQUAL, "user_id", sysUserDO.getUserId()));
        Set<String> roleIdSet = userRoleList.stream().map(SysUserRolePO::getRoleId).collect(Collectors.toSet());
        return Result.of(ResponseEnum.SUCCESS, roleIdSet);
    }

    /**
     * 获取用户对应商户下所有角色集合
     *
     * @return 树数据
     */
    @GetMapping("/getRolesUnderMerchant")
    @InOutLog("获取当前用户对应商户下所有角色集合")
    public Result<List<SysRolePO>> getRolesUnderMerchant() {
        SysUserPO sysUserDO = iSysUserService.selectByBk(SecurityUtils.getCurrentUserId());
        Set<String> subMerchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(sysUserDO.getTenantId());
        return Result.of(ResponseEnum.SUCCESS, iSysRoleService.selectList(Where.build().andAdd(QueryType.IN, "tenant_id", subMerchantIdSet)));
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
    public Result<Integer> updateUserRole(@RequestParam("userId") String userId, @RequestBody Set<String> roleIdSet) {
        String baseLog = LogHelper.getBaseLog("更新用户的角色");
        LOGGER.info("{}入参={}={}", baseLog, userId, roleIdSet);
        if (StringUtils.isBlank(userId) || CollectionUtils.isEmpty(roleIdSet)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        // 删除用户的角色信息
        Where deleteWhere = Where.build().andAdd(QueryType.EQUAL, "user_id", userId);
        Map<String, Object> updateMap = MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get();
        int deleteRows = iSysUserRoleService.logicDelete(deleteWhere, updateMap);
        LOGGER.info("{}删除用户的角色信息={}", baseLog, deleteRows);
        // 添加用户的角色信息
        roleIdSet.forEach(roleId -> {
            SysUserRolePO insert = SysUserRolePO.builder().userId(userId).roleId(roleId).build();
            insert.setCreateBy(SecurityUtils.getCurrentUserId());
            insert.setUpdateBy(SecurityUtils.getCurrentUserId());
            insert.setTenantId(SecurityUtils.getCurrentUserTenantId());
            iSysUserRoleService.insert(insert);
        });
        LOGGER.info("{}出参={}", baseLog, roleIdSet.size());
        return Result.of(ResponseEnum.SUCCESS, roleIdSet.size());
    }

}

    