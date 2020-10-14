package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.WebApplication;
import com.hb.bsmanage.web.common.enums.ResourceType;
import com.hb.bsmanage.web.dao.po.SysPermissionPO;
import com.hb.bsmanage.web.security.util.SecurityUtils;
import com.hb.unic.base.util.LogHelper;
import com.hb.unic.logger.Logger;
import com.hb.unic.logger.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * url动态权限验证
 */
@Component
public class RbacAuthorityService {

    /**
     * 日志
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(RbacAuthorityService.class);

    /**
     * HttpServletRequest
     */
    @Autowired
    private HttpServletRequest request;

    /**
     * 校验是否含有权限
     *
     * @param authentication 认真信息
     * @return true为有权限
     */
    public boolean hasPermission(Authentication authentication) {
        String baseLog = LogHelper.getBaseLog("动态权限验证");
        boolean hasPermission = false;
        if (WebApplication.testModeIsOpen()) {
            hasPermission = true;
        } else {
            List<SysPermissionPO> sysPermissionList = SecurityUtils.getCurrentUserPermissions();
            Set<String> allPermissions = sysPermissionList.stream().map(SysPermissionPO::getValue).collect(Collectors.toSet());
            //获取资源，前后端分离，所以过滤页面权限，只保留按钮权限
            Set<String> buttonPermissions = allPermissions.stream().filter(p -> ResourceType.BUTTON.getValue().equals(p)).collect(Collectors.toSet());
            for (String btnPerm : buttonPermissions) {
                AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(btnPerm);// 也可以只通过url来匹配
                if (antPathMatcher.matches(request)) {
                    hasPermission = true;
                    break;
                }
            }
        }
        LOGGER.info("{}结束={}", baseLog, hasPermission);
        return hasPermission;
    }

}