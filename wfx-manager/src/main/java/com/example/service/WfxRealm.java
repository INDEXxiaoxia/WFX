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
import org.springframework.data.redis.core.RedisTemplate;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WfxRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String REDIS_AUTHORIZATION_KEY_ACCOUNT = "redis_authorization_key_account";

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //            当结合系统来完成授权的功能时，当前登录的用户，需要从数据库中查询出它的 角色列表和权限列表
//                授权的思路：
//                    1.先根据当前登录的用户ID，查询出他所具备的角色列表
//                        * 将查询出来的角色列表（角色标识。通常不允许出来中文）(角色标识：roleFlag)
        //获取当前登录的用户信息
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

//        1.当每次进入到授权验证方法时，先去redis中查询
        //根据这个key取出的值，应该是一个hashmap类型。这hashmap会保存两条数据，分别key=角色列表和key=权限列表
        HashMap<String, List<String>> authorizitionMap = (HashMap<String, List<String>>) redisTemplate.opsForValue().get(REDIS_AUTHORIZATION_KEY_ACCOUNT);
        List<String> roleList = new ArrayList<>();
        List<String> permissionList = new ArrayList<>();
        if (authorizitionMap != null) {
            //        2.根据当前登录的用户的唯一标识 （帐号)作为key去查询
            roleList = authorizitionMap.get(userInfo.getAccount());
            permissionList = authorizitionMap.get(userInfo.getAccount());
            System.out.println("==============授权方法，从redis缓存中获取数据==============");

        } else {
            System.out.println("=============进入到授权方法。通过数据库查询================");
            authorizitionMap = new HashMap<>();
            //        4.如果不存在 ，再查数据库，同时将查出来的数据再次保存到redis
            if (roleList == null) {
                //查数据库
                roleList = getRoleNameListByRole(userInfo.getUserId());
                //从数据库中查询后，要再重新添加到redis缓存
                authorizitionMap.put(userInfo.getAccount(), roleList);
            }
            if (permissionList == null) {
                //查数据库
            }

            //当把角色列表和权限列表都进行缓存后，重新再将map对象，保存到redis中（保存时，如果数据的key是相同的，redis直接对同名的key数据进行覆盖 ）
            redisTemplate.opsForValue().set(REDIS_AUTHORIZATION_KEY_ACCOUNT, authorizitionMap);
        }
        //经过上面的判断后，最终得到当前用户的角色列表和权限列表即是 roleList和permissionList的数据
        authorizationInfo.addRoles(roleList);
        authorizationInfo.addStringPermissions(permissionList);

        return authorizationInfo;
    }


    /**
     * 根据当前用户的ID，查询所有的角色列表，并封装为集合的string类型
     *
     * @param userId
     * @return
     */
    private List<String> getRoleNameListByRole(String userId) {
        List<String> roleString = new ArrayList<>();
        //根据用户的ID，查询它具备的角色列表
        List<SysRole> roleList = sysRoleService.findRoleListByUserId(userId);
        for (SysRole sysRole : roleList) {
            roleString.add(sysRole.getRoleFlag());
        }
        return roleString;
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
