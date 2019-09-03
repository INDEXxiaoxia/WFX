package com.example.service.impl;

import com.example.mapper.SysRoleMapper;
import com.example.mapper.UserInfoMapper;
import com.example.model.SysRole;
import com.example.model.UserInfo;
import com.example.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public List<SysRole> findAll() {
        List<SysRole> roleList =  sysRoleMapper.selectAll();
        for (SysRole sysRole : roleList) {
            //根据role的角色ID 查询角色下的所有用户列表
            List<UserInfo> userInfoList = userInfoMapper.findListByRoleId(sysRole.getRoleCode());
            sysRole.setUserInfos(userInfoList);
            sysRole.setUserInfosString(getUserListOfUserName(userInfoList));
        }

        return roleList;
    }

    private String getUserListOfUserName(List<UserInfo> userInfos){
        StringBuffer sb = new StringBuffer();
        for (UserInfo userInfo : userInfos) {
            sb.append(userInfo.getUserName()+",");
        }
        return sb.toString();
    }
}
