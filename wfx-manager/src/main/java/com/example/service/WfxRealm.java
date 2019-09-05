package com.example.service;

import com.example.model.SysRole;
import com.example.model.UserInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

public class WfxRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService sysRoleService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        System.out.println("=============进入到授权方法================");
//            当结合系统来完成授权的功能时，当前登录的用户，需要从数据库中查询出它的 角色列表和权限列表
//                授权的思路：
//                    1.先根据当前登录的用户ID，查询出他所具备的角色列表
//                        * 将查询出来的角色列表（角色标识。通常不允许出来中文）(角色标识：roleFlag)
        //获取当前登录的用户信息
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();
        //根据用户的ID，查询它具备的角色列表
        List<SysRole> roleList = sysRoleService.findRoleListByUserId(userInfo.getUserId());
//
//                    2.如果有权限的设置，还要根据用户ID查询用户的权限列表
        //下面这步操作，是将当前用户的角色列表添加shiro会话中
        for (SysRole sysRole : roleList) {
            authorizationInfo.addRole(sysRole.getRoleFlag());
        }


        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("================进入到认证方法===================");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //根据用户名查询用户对象信息
        UserInfo userInfo = userInfoService.findUserInfoByUserName(token.getUsername());
        if (userInfo == null) {
            return null;
        }
        // 直接将其封装为 返回的数据类型
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(userInfo, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getAccount()), getName());
        return authenticationInfo;
    }
}
