package com.example.service;

import com.example.model.UserInfo;
import com.example.model.WxbCustomer;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class SellerRealm extends AuthorizingRealm {
    @Autowired
    CustomerService customerService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("================进入到认证方法===================");
        UsernamePasswordToken token= (UsernamePasswordToken) authenticationToken;
//        private String loginName;
//        private String loginPwd;
        WxbCustomer wxbCustomer=customerService.findCustomerByLoginName(token.getUsername());
        if (wxbCustomer==null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo=new SimpleAuthenticationInfo(wxbCustomer,wxbCustomer.getLoginPwd(),getName());
        return authenticationInfo;
    }
}
