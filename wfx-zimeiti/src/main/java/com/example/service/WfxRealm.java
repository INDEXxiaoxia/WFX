package com.example.service;

import com.example.model.UserInfo;
import com.example.model.WxbCustomer;
import com.example.model.WxbMemeber;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class WfxRealm  extends AuthorizingRealm {
    @Autowired
    WxbMemberService wxbMemberService;


    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("================进入到认证方法===================");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println("==========================="+token.getUsername());
        WxbMemeber wxbMemeber=wxbMemberService.findMemberByUsername(token.getUsername());
        if (wxbMemeber==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(wxbMemeber,wxbMemeber.getPassword(), ByteSource.Util.bytes(wxbMemeber.getAccount()),getName());

        return authenticationInfo;
    }
}
