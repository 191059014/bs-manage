package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.web.common.enums.ResponseEnum;
import com.hb.bsmanage.web.common.enums.TableEnum;
import com.hb.bsmanage.web.controller.BaseController;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.dao.po.SysRolePO;
import com.hb.bsmanage.web.dao.po.SysRolePermissionPO;
import com.hb.bsmanage.web.dao.po.SysUserPO;
import com.hb.bsmanage.web.model.dto.ElementUITree;
import com.hb.bsmanage.web.model.vo.ElementUITreeResponse;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.bsmanage.web.service.ISysMerchantService;
import com.hb.bsmanage.web.service.ISysPermissionService;
import com.hb.bsmanage.web.service.ISysRoleAccessService;
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
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
     * 用户角色关系service
     */
    @Autowired
    private ISysUserRoleService iSysUserRoleService;

    /**
     * 商户service
     */
    @Autowired
    private ISysMerchantService iSysMerchantService;

    /**
     * 角色service
     */
    @Autowired
    private ISysRoleAccessService iSysRoleAccessService;

    /**
     * 权限service
     */
    @Autowired
    private ISysPermissionService iSysPermissionService;

    /**
     * 条件分页查询
     *
     * @param role 角色信息
     * @return 结果
     */
    @PostMapping("/queryPages")
    public Result<Pagination<SysRolePO>> findPages(@RequestBody SysRolePO role,
                                                   @RequestParam("pageNum") Integer pageNum,
                                                   @RequestParam("pageSize") Integer pageSize) {
        String baseLog = LogHelper.getBaseLog("分页查询角色列表");
        LOGGER.info("{}入参={}={}={}", baseLog, role, pageNum, pageSize);
        Where where = Where.build();
        where.andAdd(QueryType.EQUAL, "role_id", role.getRoleId());
        where.andAdd(QueryType.LIKE, "role_name", role.getRoleName());
        where.andAdd(QueryType.EQUAL, "tenant_id", role.getTenantId());
        SysUserPO currentUser = SecurityUtils.getCurrentUser();
        if (currentUser.getParentId() != null) {
            LOGGER.info("{}非最高系统管理员，只能查询角色所属商户，及商户下的所有下级商户的角色={}", baseLog, currentUser.getParentId());
            Set<String> merchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(SecurityUtils.getCurrentUserTenantId());
            where.andAdd(QueryType.IN, "tenant_id", merchantIdSet);
        }
        Pagination<SysRolePO> pageResult = iSysRoleService.selectPages(where, "create_time desc", Pagination.getStartRow(pageNum, pageSize), pageSize);
        List<SysRolePO> roleList = pageResult.getData();
        iSysUserService.formatCreateByAndUpdateBy(roleList);
        LOGGER.info("{}出参={}", baseLog, pageResult);
        return Result.of(ResponseEnum.SUCCESS, pageResult);
    }

    /**
     * 添加角色
     *
     * @param role 角色信息
     * @return 结果
     */
    @PostMapping("/add")
    public Result<Integer> add(@RequestBody SysRolePO role) {
        String baseLog = LogHelper.getBaseLog("添加角色");
        LOGGER.info("{}入参={}", baseLog, role);
        if (StringUtils.isAnyBlank(role.getTenantId(), role.getRoleName())) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        role.setRoleId(KeyUtils.getUniqueKey(TableEnum.ROLE_ID.getIdPrefix()));
        role.setRoleName(role.getRoleName());
        role.setTenantId(role.getTenantId());
        role.setCreateBy(SecurityUtils.getCurrentUserId());
        role.setUpdateBy(SecurityUtils.getCurrentUserId());
        LOGGER.info("{}准备入库={}", baseLog, role);
        int addRows = iSysRoleService.insert(role);
        LOGGER.info("{}出参={}", baseLog, addRows);
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
    public Result<Integer> update(@RequestBody SysRolePO role, @RequestParam("roleId") String roleId) {
        String baseLog = LogHelper.getBaseLog("修改角色");
        LOGGER.info("{}入参={}={}", baseLog, roleId, role);
        role.setUpdateBy(SecurityUtils.getCurrentUserId());
        int updateRows = iSysRoleService.updateByBk(roleId, role);
        if (updateRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        LOGGER.info("{}出参={}", baseLog, updateRows);
        return Result.of(ResponseEnum.SUCCESS, updateRows);
    }

    /**
     * 删除角色
     *
     * @param roleId 角色ID
     * @return 结果
     */
    @GetMapping("/delete")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Integer> delete(@RequestParam("roleId") String roleId) {
        String baseLog = LogHelper.getBaseLog("删除角色");
        LOGGER.info("{}入参={}", baseLog, roleId);
        int deleteRows = iSysRoleService.logicDeleteByBk(roleId, MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get());
        if (deleteRows != 1) {
            throw new BusinessException(ResponseEnum.FAIL);
        }
        LOGGER.info("{}出参={}", baseLog, deleteRows);
        return Result.of(ResponseEnum.SUCCESS, deleteRows);
    }

    /**
     * 获取角色的权限集合
     *
     * @return 权限ID集合
     */
    @GetMapping("/getPermissionsUnderRole")
    public Result<Set<String>> getPermissionsUnderRole(@RequestParam("roleId") String roleId) {
        String baseLog = LogHelper.getBaseLog("获取角色的权限集合");
        LOGGER.info("{}入参={}", baseLog, roleId);
        List<SysRolePermissionPO> rolePermissionList = iSysRoleAccessService.selectList(Where.build().andAdd(QueryType.EQUAL, "role_id", roleId));
        Set<String> permissionIdSet = rolePermissionList.stream().map(SysRolePermissionPO::getPermissionId).collect(Collectors.toSet());
        List<SysPermissionPO> permissionList = iSysPermissionService.getPermissionListByPermissionIdSet(permissionIdSet);
        Set<String> parentPermissionIdSet = permissionList.stream().map(SysPermissionPO::getParentId).collect(Collectors.toSet());
        // 只返回叶子节点，防止父节点选中，导致子节点全部选中
        permissionIdSet.removeAll(parentPermissionIdSet);
        LOGGER.info("{}出参={}", baseLog, permissionIdSet);
        return Result.of(ResponseEnum.SUCCESS, permissionIdSet);
    }

    /**
     * 获取角色对应商户下所有权限集合
     *
     * @return 权限集合
     */
    @GetMapping("/getPermissionUnderMerchant")
    @InOutLog("获取角色对应商户下所有权限集合")
    public Result<List<SysPermissionPO>> getPermissionUnderMerchant(@RequestParam("roleId") String roleId) {
        SysRolePO sysRoleDO = iSysRoleService.selectByBk(roleId);
        return Result.of(ResponseEnum.SUCCESS, iSysPermissionService.selectList(Where.build().andAdd(QueryType.EQUAL, "tenant_id", sysRoleDO.getTenantId())));
    }

    /**
     * 更新用户的角色
     *
     * @param roleId          角色
     * @param permissionIdSet 权限id集合
     * @return 更新结果
     */
    @PostMapping("/updateRolePermission")
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Result<Integer> updateRolePermission(@RequestParam("roleId") String roleId, @RequestBody Set<String> permissionIdSet) {
        String baseLog = LogHelper.getBaseLog("更新用户的角色");
        LOGGER.info("{}入参={}={}", baseLog, roleId, permissionIdSet);
        if (StringUtils.isBlank(roleId) || CollectionUtils.isEmpty(permissionIdSet)) {
            return Result.of(ResponseEnum.PARAM_ILLEGAL);
        }
        // 删除角色的权限信息
        Where deleteWhere = Where.build().andAdd(QueryType.EQUAL, "role_id", roleId);
        Map<String, Object> updateMap = MapBuilder.build().add("updateBy", SecurityUtils.getCurrentUserId()).get();
        int deleteRows = iSysRoleAccessService.logicDelete(deleteWhere, updateMap);
        LOGGER.info("{}删除角色的权限信息={}", baseLog, deleteRows);
        // 添加角色的权限信息
        permissionIdSet.forEach(permissionId -> {
            SysRolePermissionPO insert = SysRolePermissionPO.builder().roleId(roleId).permissionId(permissionId).build();
            insert.setCreateBy(SecurityUtils.getCurrentUserId());
            insert.setUpdateBy(SecurityUtils.getCurrentUserId());
            insert.setTenantId(SecurityUtils.getCurrentUserTenantId());
            iSysRoleAccessService.insert(insert);
        });
        LOGGER.info("{}出参={}", baseLog, permissionIdSet.size());
        return Result.of(ResponseEnum.SUCCESS, permissionIdSet.size());
    }

    /**
     * 获取当前用户权限树
     *
     * @return 权限树
     */
    @InOutLog("获取当前用户权限树")
    @GetMapping("/getPermissionTreeUnderMerchant")
    public Result<ElementUITreeResponse> getPermissionTreeUnderMerchant() {
        ElementUITreeResponse response = new ElementUITreeResponse();
        SysUserPO sysUserPO = iSysUserService.selectByBk(SecurityUtils.getCurrentUserId());
        Set<String> subMerchantIdSet = iSysMerchantService.getCurrentSubMerchantIdSet(sysUserPO.getTenantId());
        List<SysPermissionPO> permissionList = iSysPermissionService.selectList(Where.build().andAdd(QueryType.IN, "tenant_id", subMerchantIdSet));
        Set<String> permissionIdSet = permissionList.stream().map(SysPermissionPO::getPermissionId).collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(permissionIdSet)) {
            return Result.of(ResponseEnum.SUCCESS, response);
        }
        Where where = Where.build().andAdd(QueryType.IN, "permission_id", permissionIdSet);
        List<SysPermissionPO> allList = iSysPermissionService.selectList(where, "create_time asc");
        List<SysPermissionPO> topList = allList.stream().filter(access -> StringUtils.isBlank(access.getParentId())).collect(Collectors.toList());
        List<ElementUITree> treeDataList = findTreeCycle(allList, topList);
        response.setTreeDataList(treeDataList);
        return Result.of(ResponseEnum.SUCCESS, response);
    }

    /**
     * 递归获取权限树
     *
     * @return 权限树
     */
    private List<ElementUITree> findTreeCycle(List<SysPermissionPO> allList, List<SysPermissionPO> childList) {
        List<ElementUITree> treeDataList = new ArrayList<>();
        for (SysPermissionPO access : childList) {
            ElementUITree treeData = ElementUITree.builder()
                    .id(access.getPermissionId())
                    .label(access.getPermissionName())
                    .build();
            List<SysPermissionPO> cList = allList.stream().filter(acc -> access.getPermissionId().equals(acc.getParentId())).collect(Collectors.toList());
            treeData.setChildren(findTreeCycle(allList, cList));
            treeDataList.add(treeData);
        }
        return treeDataList.size() > 0 ? treeDataList : null;
    }

}

