package com.hb.bsmanage.web.controller.sys;

import com.hb.bsmanage.api.ISysPermissionService;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.enums.AccessType;
import com.hb.bsmanage.model.model.Menu;
import com.hb.bsmanage.model.response.MenuDataResponse;
import com.hb.bsmanage.web.common.BaseController;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.unic.base.common.Result;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 用户controller
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
     * 获取私人的所有菜单信息
     *
     * @return 菜单信息列表
     */
    @GetMapping("/getPrivateMenuDatas")
    public Result<MenuDataResponse> getPrivateMenuDatas() {
        MenuDataResponse response = new MenuDataResponse();
        List<SysPermissionDO> currentUserAccesses = SecurityUtils.getCurrentUserAccesses();
        Predicate<SysPermissionDO> predicate = access -> StringUtils.isBlank(access.getParentId()) && AccessType.PAGE.getValue().equals(access.getResourceType());
        Set<SysPermissionDO> firstLevelAccesses = currentUserAccesses.stream().filter(predicate).collect(Collectors.toSet());
        Set<Menu> menuSet = new HashSet<>();
        firstLevelAccesses.forEach(access -> {
            Menu menu = Menu.builder().index(access.getPermissionId())
                    .name(access.getPermissionName())
                    .icon(access.getIcon())
                    .url(access.getUrl())
                    .parentIndex(access.getParentId())
                    .children(findChildrenCycle(currentUserAccesses, access))
                    .build();
            menuSet.add(menu);
        });
        response.setMenuDatas(menuSet);
        return Result.of(ResponseEnum.SUCCESS, response);
    }

    /**
     * 递归查找菜单
     *
     * @param allAccess     所有权限
     * @param currentAccess 当前权限信息
     * @return 菜单列表
     */
    private Set<Menu> findChildrenCycle(List<SysPermissionDO> allAccess, SysPermissionDO currentAccess) {
        Set<Menu> menuSet = new HashSet<>();
        allAccess.forEach(access -> {
            if (currentAccess.getPermissionId().equals(access.getParentId())) {
                Menu menu = Menu.builder().index(access.getPermissionId())
                        .name(access.getPermissionName())
                        .icon(access.getIcon())
                        .url(access.getUrl())
                        .parentIndex(access.getParentId())
                        .children(findChildrenCycle(allAccess, access))
                        .build();
                menuSet.add(menu);
            }
        });
        return menuSet.size() > 0 ? menuSet : null;
    }

}

    