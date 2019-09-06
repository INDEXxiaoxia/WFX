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

    private static final String REDIS_ROLELIST = "redis_roleList";//保存所有用户的角色列表（key)
    private static final String REDIS_PERMISSIONLIST = "redis_permisonList";//保存所有用户的权限列表(key)

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        //1、获取当前登录的用户信息
        UserInfo userInfo = (UserInfo) SecurityUtils.getSubject().getPrincipal();

//        1.当每次进入到授权验证方法时，先去redis中查询
            // 1)先根据用户的帐号，查询角色，如果redis中，查不出来，则去数据库中查
            HashMap<String,List> rolesMap = (HashMap<String, List>) redisTemplate.boundValueOps(REDIS_ROLELIST);
            //2）注意：获取的rolesMap，它里的key就是每个用户的帐号
//            if (rolesMap == null){//说明所有用户的角色都没有被缓存,那么查数据库
//
//            }else{//如果这个不为空，说明有用户的角色是在缓存中，再次判断，当前用户的角色列表是否被缓存
//                if(rolesMap.get(userInfo.getAccount()) == null){//说明 当前用户的角色没有被缓存，当前用户的权限也要查数据库
//
//                }
//            }
            //上面的判断合并之后，优化判断之后的效果，如下
        List<String> roleList = new ArrayList<>();
        if(rolesMap == null || rolesMap.get(userInfo.getAccount()) == null){//两个条件，任何一个为null，则说明当前用户角色没有被缓存
            //根据用户ID查询角色列表
            roleList = getRoleNameListByRole(userInfo.getUserId());
            //查出来后，将当前角色保存到redis中
            if (rolesMap == null){
                rolesMap = new HashMap<>();
            }
            //上面查询当前角色列表后，再将该角色列表数据与当前用户的ID，作为value-key 保存到redis
            rolesMap.put(userInfo.getAccount(),roleList);
            //更新Redis中的数据
            redisTemplate.boundValueOps(REDIS_ROLELIST).set(rolesMap);
        }else{
            roleList = ((HashMap<String, List>) redisTemplate.boundValueOps(REDIS_ROLELIST)).get(userInfo.getAccount());
        }
        //将最终取出来的数据给 授权的返回对象
        authorizationInfo.addRoles(roleList);

        //判断权限的查询

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
