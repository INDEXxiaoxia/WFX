//package com.example.service;
//
//import org.apache.shiro.authc.*;
//import org.apache.shiro.authz.AuthorizationInfo;
//import org.apache.shiro.realm.AuthorizingRealm;
//import org.apache.shiro.subject.PrincipalCollection;
//import tk.mybatis.mapper.entity.Example;
//
//public class WfxRealm extends AuthorizingRealm {
//
//
//    @Override
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
//        return null;
//    }
//
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
//        System.out.println("================进入到认证方法===================");
//
//        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
//
//        // 直接将其封装为 返回的数据类型
//        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo("admin", "123456", getName());
//        return authenticationInfo;
//    }
//}
