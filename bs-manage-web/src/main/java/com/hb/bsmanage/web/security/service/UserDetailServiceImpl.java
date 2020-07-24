package com.hb.bsmanage.web.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * 用户信息
 *
 * @author Mr.Huang
 * @version v0.1, UserDetailServiceImpl.java, 2020/6/1 15:23, create by huangbiao.
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("456");
        System.out.println(encode);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
//        User user = this.getUserByUserName(s);
//        log.info("用户信息：{}", user);
//        List<String> roleList = this.getRoleListByRoleIdArr(s);
//        log.info("角色信息：{}", roleList);
//        List<Permission> permissionList = this.getPermissionListByPermissionIdArr(s);
//        log.info("权限信息：{}", permissionList);
//        return new UserPrincipal(user, roleList, permissionList);
        return null;
    }

}

    