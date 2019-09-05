package com.example.service.impl;

import com.example.mapper.SysUserRoleMapper;
import com.example.mapper.UserInfoMapper;
import com.example.model.SysUserRole;
import com.example.model.UserInfo;
import com.example.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public UserInfo findUserInfoByUserName(String userName) {
        Example example = new Example(UserInfo.class);
        example.and().andEqualTo("account", userName);
        return userInfoMapper.selectOneByExample(example);
    }

    @Override
    public List<UserInfo> findListByParam(UserInfo userInfo) {
        //根据条件进行查询
        Example example = new Example(UserInfo.class);
        if (userInfo != null) {
            //根据用户名和用户帐号 进行模糊查询
            if (StringUtils.isNotBlank(userInfo.getUserName())) {
                example.and().andLike("userName", "%" + userInfo.getUserName() + "%");
            }
            if (StringUtils.isNotBlank(userInfo.getAccount())) {
                example.and().andLike("account", "%" + userInfo.getAccount() + "%");
            }
        }
        return userInfoMapper.selectByExample(example);
    }

    @Override
    public void save(UserInfo userInfo) {
//            1.控制层接收到当前要添加的用户信息后，在保存到数据库时，是经过下面几步的操作：
//                1）将用户信息保存到 user_info表
        //添加操作前，先生成唯一的主键
        userInfo.setUserId(UUID.randomUUID().toString().replaceAll("-", ""));
//        3)将当前添加的用户密码，进行shiro格式的加密
        userInfo.setPassword(secretShiroPass(userInfo.getAccount(), userInfo.getPassword()));
        userInfo.setEnabled("1");//添加的帐号，默认“启用”
        userInfoMapper.insert(userInfo);
//                2)将用户-角色的关系保存到 sys_user_role表
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId(userInfo.getUserId());
        sysUserRole.setRoleId(userInfo.getRoleId());
        sysUserRoleMapper.insert(sysUserRole);
//
    }

    /**
     * 将原始密码，按照shiro框架的加密方式，进行加密
     *
     * @param saltStr  盐值，加密的密钥
     * @param password 被加密的原始密码
     * @return
     */
    private String secretShiroPass(String saltStr, String password) {
        String hashAlgorithmName = "MD5";//加密方式
        ByteSource salt = ByteSource.Util.bytes(saltStr);//以账号作为盐值

        int hashIterations = 56;// 加密56次（注意这里配置的值要与shiro的配置文件中配置的值保持一致。否则无法解密

        SimpleHash hash = new SimpleHash(hashAlgorithmName, password, salt, hashIterations);
        return hash.toString();
    }

    //推特雪花算法（分布式事务时，生成唯一主键）
    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-", "");
        System.out.println(uuid);
    }
}
