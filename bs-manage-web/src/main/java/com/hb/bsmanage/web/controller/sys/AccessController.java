package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.web.common.enums.ResourceType;
import com.hb.bsmanage.web.common.enums.ErrorCode;
import com.hb.bsmanage.web.common.enums.PkPrefix;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.model.dto.ElementUIMenu;
import com.hb.bsmanage.web.model.vo.ElementUIMenuResponse;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.bsmanage.web.service.ISysMerchantService;
import com.hb.bsmanage.web.service.ISysPermissionService;
import com.hb.bsmanage.web.service.ISysUserService;
import com.hb.mybatis.enums.QueryType;
import com.hb.mybatis.helper.Where;
import com.hb.unic.base.common.Result;
import com.hb.unic.base.exception.BusinessException;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.util.easybuild.MapBuilder;
import com.hb.unic.util.easybuild.SetBuilder;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
     * 获取私人的所有菜单信息
     *
     * @return 菜单信息列表
     */
    @GetMapping("/getPrivateMenuDatas")
    public Result<ElementUIMenuResponse> getPrivateMenuDatas() {
        String baseLog = LogHelper.getBaseLog("获取每个用户的所有菜单");
        ElementUIMenuResponse response = new ElementUIMenuResponse();
        Set<String> permissionIdSet = SecurityUtils.getCurrentUserPermissionIdSet();
        LOGGER.info("{}当前用户权限ID集合={}", baseLog, permissionIdSet);
        if (CollectionUtils.isEmpty(permissionIdSet)) {
            return Result.of(ErrorCode.SUCCESS, response);
        }
        Where where = Where.build();
        where.andAdd(QueryType.IN, "permission_id", permissionIdSet);
        where.andAdd(QueryType.IN, "resource_type", SetBuilder.build().add(ResourceType.FOLDER.getValue(), ResourceType.PAGE.getValue()).get());
        List<SysPermissionPO> allList = iSysPermissionService.selectList(where, "create_time asc");
        List<SysPermissionPO> topList = allList.stream().filter(access -> StringUtils.isBlank(access.getParentId())).collect(Collectors.toList());
        List<ElementUIMenu> menuList = findChildrenMenuCycle(allList, topList);
        response.setMenuDatas(menuList);
        LOGGER.info("{}出参={}", baseLog, response);
        return Result.of(ErrorCode.SUCCESS, response);
    }

    /**
     * 递归查找菜单
     *
     * @param allList   所有权限
     * @param childList 当前权限信息
     * @return 菜单列表
     */
    private List<ElementUIMenu> findChildrenMenuCycle(List<SysPermissionPO> allList, List<SysPermissionPO> childList) {
        List<ElementUIMenu> menuList = new ArrayList<>();
        childList.forEach(access -> {
            ElementUIMenu menu = ElementUIMenu.builder().index(access.getPermissionId())
                    .name(access.getPermissionName())
                    .icon(access.getIcon())
                    .url(access.getUrl())
                    .keepAlive(access.getKeepAlive())
                    .parentIndex(access.getParentId())
                    .build();
            List<SysPermissionPO> cList = allList.stream().filter(acc -> access.getPermissionId().equals(acc.getParentId())).collect(Collectors.toList());
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
    public Result<Pagination<SysPermissionPO>> findPages(@RequestBody SysPermissionPO permission,
                                                         @RequestParam("pageNum") Integer pageNum,
                                                         @RequestParam("pageSize") Integer pageSize) {
        String baseLog = LogHelper.getBaseLog("分页查询权限列表");
        LOGGER.info("{}入参={}={}={}", baseLog, permission, pageNum, pageSize);
        if (!Pagination.verify(pageNum, pageSize)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "permission_id", permission.getPermissionId());
        where.andAdd(QueryType.LIKE, "permission_name", permission.getPermissionName());
        where.andAdd(QueryType.EQUAL, "resource_type", permission.getResourceType());
        where.andAdd(QueryType.EQUAL, "tenant_id", permission.getTenantId());
        if (SecurityUtils.getCurrentUserParentId() != null) {
            // 非最高系统管理员，只能查询权限所属商户，及商户下的所有下级商户的权限
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "tenant_id", merchantIdSet);
        }
        Pagination<SysPermissionPO> pageResult = iSysPermissionService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);
        iSysUserService.formatCreateByAndUpdateBy(pageResult.getData());
        LOGGER.info("{}出参={}", baseLog, pageResult);
        return Result.of(ErrorCode.SUCCESS, pageResult);
    }

    /**
     * 添加权限
     *
     * @param permission 权限信息
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysPermissionPO permission) {
        String baseLog = LogHelper.getBaseLog("添加权限");
        LOGGER.info("{}入参={}", baseLog, permission);
        if (StringUtils.isAnyBlank(permission.getTenantId(), permission.getPermissionName(), permission.getResourceType(), permission.getValue())) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        permission.setPermissionId(KeyUtils.getUniqueKey(PkPrefix.PERMISSION_ID.getValue()));
        permission.setCreateBy(SecurityUtils.getCurrentUserId());
        permission.setUpdateBy(SecurityUtils.getCurrentUserId());
        LOGGER.info("{}准备入库={}", baseLog, permission);
        int addRows = iSysPermissionService.insert(permission);
        LOGGER.info("{}出参={}", baseLog, addRows);
        return Result.of(ErrorCode.SUCCESS, addRows);
    }

    /**
     * 修改权限
     *
     * @param permission 权限
     * @return 结果
     */
    @PostMapping("/update")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Integer> update(@RequestBody SysPermissionPO permission, @RequestParam("permissionId") String permissionId) {
        String baseLog = LogHelper.getBaseLog("修改权限");
        LOGGER.info("{}入参={}={}", baseLog, permissionId, permission);
        if (StringUtils.isBlank(permissionId)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        permission.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysPermissionService.updateByBk(permissionId, permission);
        if (updateRows != 1) {
            throw new BusinessException(ErrorCode.FAIL);
        }
        LOGGER.info("{}出参={}", baseLog, updateRows);
        return Result.of(ErrorCode.SUCCESS, updateRows);
    }

    /**
     * 删除权限
     *
     * @param permissionId 权限ID
     * @return 结果
     */
    @GetMapping("/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Integer> delete(@RequestParam("permissionId") String permissionId) {
        String baseLog = LogHelper.getBaseLog("删除权限");
        LOGGER.info("{}入参={}", baseLog, permissionId);
        if (StringUtils.isBlank(permissionId)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        int deleteRows = iSysPermissionService.logicDeleteByBk(permissionId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        if (deleteRows != 1) {
            throw new BusinessException(ErrorCode.FAIL);
        }
        LOGGER.info("{}出参={}", baseLog, deleteRows);
        return Result.of(ErrorCode.SUCCESS);
    }

    /**
     * 通过资源类型获取当前商户下的资源
     *
     * @param resourceType 资源类型
     * @return 资源
     */
    @GetMapping("/getResourcesUnderMerchantByResourceType")
    public Result<List<SysPermissionPO>> getResourcesUnderMerchantByResourceType(@RequestParam("resourceType") String resourceType, @RequestParam("tenantId") String tenantId) {
        String baseLog = LogHelper.getBaseLog("通过资源类型获取当前商户下的资源");
        LOGGER.info("{}入参={}={}", baseLog, resourceType, tenantId);
        if (StringUtils.isAnyBlank(resourceType, tenantId)) {
            return Result.of(ErrorCode.PARAM_ILLEGAL);
        }
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "resource_type", resourceType);
        where.andAdd(QueryType.EQUAL, "tenant_id", tenantId);
        List<SysPermissionPO> list = iSysPermissionService.selectList(where, "create_time desc");
        LOGGER.info("{}出参={}", baseLog, list);
        return Result.of(ErrorCode.SUCCESS, list);
    }

}

    