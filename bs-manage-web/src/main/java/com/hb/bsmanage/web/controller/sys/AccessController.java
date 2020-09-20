package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.service.ISysMerchantService;
import com.hb.bsmanage.api.service.ISysPermissionService;
import com.hb.bsmanage.api.service.ISysRoleAccessService;
import com.hb.bsmanage.api.service.ISysUserService;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.dobj.SysUserDO;
import com.hb.bsmanage.model.enums.ResourceType;
import com.hb.bsmanage.model.enums.TableEnum;
import com.hb.bsmanage.model.model.ElementUIMenu;
import com.hb.bsmanage.model.response.ElementUIMenuResponse;
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
import com.hb.unic.util.easybuild.SetBuilder;
import com.hb.unic.util.util.KeyUtils;
import com.hb.unic.util.util.Pagination;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限controller
 *
 * @version v0.1, 2020/7/24 15:05, create by huangbiao.
 */
@RestController
@RequestMapping("bs/auth/access")
public class AccessController extends BaseController {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccessController.class);

    /**
     * 权限service
     */
    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 商户service
     */
    @Autowired
    private ISysMerchantService iSysMerchantService;

    /**
     * 用户service
     */
    @Autowired
    private ISysUserService iSysUserService;

    /**
     * 角色权限关系service
     */
    @Autowired
    private ISysRoleAccessService iSysRoleAccessService;

    /**
     * 获取私人的所有菜单信息
     *
     * @return 菜单信息列表
     */
    @GetMapping("/getPrivateMenuDatas")
    public Result<ElementUIMenuResponse> getPrivateMenuDatas() {
        ElementUIMenuResponse response = new ElementUIMenuResponse();
        Set<String> permissionIdSet = SecurityUtils.getCurrentUserPermissions();
        if (CollectionUtils.isEmpty(permissionIdSet)) {
            return Result.of(ResponseEnum.SUCCESS, response);
        }
        Where where = Where.build().andAdd(QueryType.IN, "permission_id", permissionIdSet);
        where.andAdd(QueryType.IN, "resource_type", SetBuilder.build().add(ResourceType.FOLDER.getValue(), ResourceType.PAGE.getValue()).get());
        List<SysPermissionDO> allList = iSysPermissionService.selectList(where, "create_time asc");
        List<SysPermissionDO> topList = allList.stream().filter(access -> StringUtils.isBlank(access.getParentId())).collect(Collectors.toList());
        List<ElementUIMenu> menuList = findChildrenMenuCycle(allList, topList);
        response.setMenuDatas(menuList);
        return Result.of(ResponseEnum.SUCCESS, response);
    }

    /**
     * 递归查找菜单
     *
     * @param allList   所有权限
     * @param childList 当前权限信息
     * @return 菜单列表
     */
    private List<ElementUIMenu> findChildrenMenuCycle(List<SysPermissionDO> allList, List<SysPermissionDO> childList) {
        List<ElementUIMenu> menuList = new ArrayList<>();
        childList.forEach(access -> {
            ElementUIMenu menu = ElementUIMenu.builder().index(access.getPermissionId())
                    .name(access.getPermissionName())
                    .icon(access.getIcon())
                    .url(access.getUrl())
                    .parentIndex(access.getParentId())
                    .build();
            List<SysPermissionDO> cList = allList.stream().filter(acc -> access.getPermissionId().equals(acc.getParentId())).collect(Collectors.toList());
            menu.setChildren(findChildrenMenuCycle(allList, cList));
            menuList.add(menu);

        });
        return menuList.size() > 0 ? menuList : null;
    }

    /**
     * 条件分页查询
     *
     * @param permission 权限信息
     * @return 结果
     */
    @PostMapping("/queryPages")
    public Result<Pagination<SysPermissionDO>> findPages(@RequestBody SysPermissionDO permission,
                                                         @RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize) {
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "permission_id", permission.getPermissionId());
        where.andAdd(QueryType.LIKE, "permission_name", permission.getPermissionName());
        where.andAdd(QueryType.EQUAL, "resource_type", permission.getResourceType());
        where.andAdd(QueryType.EQUAL, "tenant_id", permission.getTenantId());
        SysUserDO currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getParentId() != null) {
            // 非最高系统管理员，只能查询权限所属商户，及商户下的所有下级商户的权限
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "tenant_id", merchantIdSet);
        }
        Pagination<SysPermissionDO> pageResult = iSysPermissionService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);

        List<SysPermissionDO> roleList = pageResult.getData();
        if (CollectionUtils.isNotEmpty(roleList)) {
            Set<String> userIdSet = new HashSet<>();
            roleList.forEach(permissionDO -> {
                userIdSet.add(permissionDO.getCreateBy());
                userIdSet.add(permissionDO.getUpdateBy());
            });
            if (CollectionUtils.isNotEmpty(userIdSet)) {
                Map<String, SysUserDO> userMap = iSysUserService.getUserMapByUserIdSet(userIdSet);
                roleList.forEach(permissionDO -> {
                    SysUserDO createBy = userMap.get(permissionDO.getCreateBy());
                    permissionDO.setCreateBy(createBy == null ? null : createBy.getUserName());
                    SysUserDO updateBy = userMap.get(permissionDO.getUpdateBy());
                    permissionDO.setUpdateBy(updateBy == null ? null : updateBy.getUserName());
                });
            }
        }
        return Result.of(ResponseEnum.SUCCESS, pageResult);
    }

    /**
     * 添加权限
     *
     * @param permission 权限信息
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysPermissionDO permission) {
        if (StringUtils.isAnyBlank(permission.getPermissionName(), permission.getResourceType(), permission.getValue())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        permission.setPermissionId(KeyUtils.getUniqueKey(TableEnum.PERMISSION_ID.getIdPrefix()));
        permission.setTenantId(SecurityUtils.getCurrentUserTenantId());
        permission.setCreateBy(SecurityUtils.getCurrentUserId());
        permission.setUpdateBy(SecurityUtils.getCurrentUserId());
        int addRows = iSysPermissionService.insert(permission);
        return Result.of(ResponseEnum.SUCCESS, addRows);
    }

    /**
     * 修改权限
     *
     * @param permission 权限
     * @return 结果
     */
    @PostMapping("/update")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result update(@RequestBody SysPermissionDO permission, @RequestParam("permissionId") String permissionId) {
        permission.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysPermissionService.updateByBk(permissionId, permission);
        if (updateRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     * @return 结果
     */
    @GetMapping("/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result delete(@RequestParam("permissionId") String permissionId) {
        int deleteRows = iSysPermissionService.logicDeleteByBk(permissionId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        if (deleteRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        return Result.of(ResponseEnum.SUCCESS);
    }

    /**
     * 通过资源类型获取当前商户下的资源
     *
     * @param resourceType 资源类型
     * @return 资源
     */
    @GetMapping("/getResourcesUnderMerchantByResourceType")
    public Result<List<SysPermissionDO>> getResourcesUnderMerchantByResourceType(@RequestParam("resourceType") String resourceType) {
        Where where = Where.build()
                .andAdd(QueryType.EQUAL, "tenant_id", SecurityUtils.getCurrentUserTenantId())
                .andAdd(QueryType.EQUAL, "resource_type", resourceType);
        List<SysPermissionDO> list = iSysPermissionService.selectList(where, "create_time desc");
        return Result.of(ResponseEnum.SUCCESS, list);
    }

}

    