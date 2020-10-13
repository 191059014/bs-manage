package com.hb.bsmanage.web.security.service;

import com.hb.bsmanage.web.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * url动态权限验证
 */
@Component
public class RbacAuthorityService {

    @Autowired
    private RequestMappingHandlerMapping mapping;

    @Autowired
    private HttpServletRequest request;

    /**
     * 校验是否含有权限
     *
     * @param authentication 认真信息
     * @return true为有权限
     */
    public boolean hasPermission(Authentication authentication) {
        if (WebApplication.testModeIsOpen()) {
            System.out.println("RbacAuthorityService.hasPermission（模拟）");
            return false;
        }
        Object userInfo = authentication.getPrincipal();
        boolean hasPermission = false;

//        if (userInfo instanceof UserDetails) {
//            UserPrincipal principal = (UserPrincipal) userInfo;
//            String userId = principal.getUser().getUserId();
//
//            List<Role> roles = this.selectByUserId(userId);
//            List<String> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
//            List<Permission> permissions = this.selectByRoleIdList(roleIds);
//
//            //获取资源，前后端分离，所以过滤页面权限，只保留按钮权限
//            List<Permission> btnPerms = permissions.stream()
//                    // 过滤页面权限
//                    .filter(permission -> Objects.equals(permission.getType(), Consts.BUTTON))
//                    // 过滤 URL 为空
//                    .filter(permission -> !StrUtils.isEmpty(permission.getUrl()))
//                    // 过滤 METHOD 为空
//                    .filter(permission -> !StrUtils.isEmpty(permission.getMethod()))
//                    .collect(Collectors.toList());
//
//            for (Permission btnPerm : btnPerms) {
//                AntPathRequestMatcher antPathMatcher = new AntPathRequestMatcher(btnPerm.getUrl(), btnPerm.getMethod());// 也可以只通过url来匹配
//                if (antPathMatcher.matches(request)) {
//                    hasPermission = true;
//                    break;
//                }
//            }
//        }
        System.out.println("RbacAuthorityService.hasPermission: " + hasPermission);
        return hasPermission;
    }

}