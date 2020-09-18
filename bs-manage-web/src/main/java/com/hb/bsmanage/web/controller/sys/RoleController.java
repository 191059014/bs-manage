package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.service.ISysMerchantService;
import com.hb.bsmanage.api.service.ISysRoleService;
import com.hb.bsmanage.api.service.ISysUserService;
import com.hb.bsmanage.model.dobj.SysRoleDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.enums.TableEnum;
import com.hb.bsmanage.model.response.TreeDataResponse;
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
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 角色controller
 *
 * @version v0.1, 2020/7/24 15:05, create by huangbiao.
 */
@RestController
@RequestMapping("bs/auth/role")
public class RoleController extends BaseController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);

    /**
     * 角色service
     */
    @Autowired
    private ISysRoleService iSysRoleService;

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
     * 条件分页查询
     *
     * @param role 角色信息
     * @return 结果
     */
    @PostMapping("/queryPages")
    public Result<Pagination<SysRoleDO>> findPages(@RequestBody SysRoleDO role,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "role_id", role.getRoleId());
        where.andAdd(QueryType.LIKE, "role_name", role.getRoleName());
        where.andAdd(QueryType.EQUAL, "tenant_id", role.getTenantId());
        SysUserDO currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getParentId() != null) {
            // 非最高系统管理员，只能查询角色所属商户，及商户下的所有下级商户的角色
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "tenant_id", merchantIdSet);
        }
        Pagination<SysRoleDO> pageResult = iSysRoleService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);

        List<SysRoleDO> roleList = pageResult.getData();
        if (CollectionUtils.isNotEmpty(roleList)) {
            Set<String> userIdSet = new HashSet<>();
            roleList.forEach(roleDO -> {
                userIdSet.add(roleDO.getCreateBy());
                userIdSet.add(roleDO.getUpdateBy());
            });
            if (CollectionUtils.isNotEmpty(userIdSet)) {
                Map<String, SysUserDO> userMap = iSysUserService.getUserMapByUserIdSet(userIdSet);
                roleList.forEach(roleDO -> {
                    SysUserDO createBy = userMap.get(roleDO.getCreateBy());
                    roleDO.setCreateBy(createBy == null ? null : createBy.getUserName());
                    SysUserDO updateBy = userMap.get(roleDO.getUpdateBy());
                    roleDO.setUpdateBy(updateBy == null ? null : updateBy.getUserName());
                });
            }
        }
        return Result.of(ResponseEnum.SUCCESS, pageResult);
    }

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysRoleDO role) {
        if (StringUtils.isAnyBlank(role.getRoleName())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        role.setRoleId(KeyUtils.getUniqueKey(TableEnum.ROLE_ID.getIdPrefix()));
        role.setRoleName(role.getRoleName());
        role.setTenantId(SecurityUtils.getCurrentUserTenantId());
        role.setCreateBy(SecurityUtils.getCurrentUserId());
        role.setUpdateBy(SecurityUtils.getCurrentUserId());
        int addRows = iSysRoleService.insert(role);
        return Result.of(ResponseEnum.SUCCESS, addRows);
    }

    /**
     * 修改角色
     *
     * @param role 角色
     * @return 结果
     */
    @PostMapping("/update")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result update(@RequestBody SysRoleDO role, @RequestParam("roleId") String roleId) {
        role.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysRoleService.updateByBk(roleId, role);
        if (updateRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @GetMapping("/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result delete(@RequestParam("roleId") String roleId) {
        int deleteRows = iSysRoleService.logicDeleteByBk(roleId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        if (deleteRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

}

    