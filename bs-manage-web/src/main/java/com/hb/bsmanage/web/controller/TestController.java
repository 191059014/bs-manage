package com.hb.bsmanage.web.controller;

import com.hb.bsmanage.api.service.ISysPermissionService;
import com.hb.bsmanage.model.dobj.SysPermissionDO;
import com.hb.bsmanage.model.model.TreeData;
import com.hb.bsmanage.model.response.TreeDataResponse;
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
    public Result<TreeDataResponse> testGetPermissionTree() {
        List<SysPermissionDO> list = iSysPermissionService.selectList(null);
        TreeDataResponse response = new TreeDataResponse();
        List<SysPermissionDO> topList = list.stream().filter(access -> StringUtils.isBlank(access.getParentId())).collect(Collectors.toList());
        List<TreeData> treeDataList = findTreeCycle(list, topList);
        response.setTreeDataList(treeDataList);
        return Result.of(ResponseEnum.SUCCESS, response);
    }

    private List<TreeData> findTreeCycle(List<SysPermissionDO> allList, List<SysPermissionDO> childList) {
        List<TreeData> treeDataList = new ArrayList<>();
        for (SysPermissionDO access : childList) {
            TreeData treeData = TreeData.builder()
                    .id(access.getPermissionId())
                    .label(access.getPermissionName())
                    .build();
            List<SysPermissionDO> cList = allList.stream().filter(acc -> access.getPermissionId().equals(acc.getParentId())).collect(Collectors.toList());
            treeData.setChildren(findTreeCycle(allList, cList));
            treeDataList.add(treeData);
        }
        return treeDataList.size() > 0 ? treeDataList : null;
    }

}
