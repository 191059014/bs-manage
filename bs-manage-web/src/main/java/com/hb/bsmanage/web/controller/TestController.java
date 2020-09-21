package com.hb.bsmanage.web.controller;

import com.hb.bsmanage.api.service.ISysPermissionService;
import com.hb.bsmanage.model.dto.ElementUITree;
import com.hb.bsmanage.model.po.SysPermissionPO;
import com.hb.bsmanage.model.vo.response.ElementUITreeResponse;
import com.hb.bsmanage.web.common.ResponseEnum;
import com.hb.unic.base.common.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author guoll
 * @date 2020/9/19
 */
@RestController
@RequestMapping("bs/test")
public class TestController {

    @Autowired
    private ISysPermissionService iSysPermissionService;

    @GetMapping("/testGetPermissionTree")
    public Result<ElementUITreeResponse> testGetPermissionTree() {
        List<SysPermissionPO> list = iSysPermissionService.selectList(null);
        ElementUITreeResponse response = new ElementUITreeResponse();
        List<SysPermissionPO> topList = list.stream().filter(access -> StringUtils.isBlank(access.getParentId())).collect(Collectors.toList());
        List<ElementUITree> treeDataList = findTreeCycle(list, topList);
        response.setTreeDataList(treeDataList);
        return Result.of(ResponseEnum.SUCCESS, response);
    }

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
