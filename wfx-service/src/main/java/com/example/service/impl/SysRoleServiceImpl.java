package com.example.service.impl;

import com.example.mapper.SysRoleFunMapper;
import com.example.mapper.SysRoleMapper;
import com.example.mapper.UserInfoMapper;
import com.example.model.SysRole;
import com.example.model.SysRoleFun;
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

    @Autowired
    private SysRoleFunMapper sysRoleFunMapper;

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

    @Override
    public void saveRole(SysRole role) {//将数据添加到数据库中
        //1、创建一个新的角色(sys_role)
        long roleId = System.currentTimeMillis();//获取当前日期的时间戳（重复的概念非常中）
        role.setRoleCode(roleId+"");
        role.setRoleType(2L);
        //添加角色
        sysRoleMapper.insert(role);
        //2.创建这个角色的权限到 sys_role_fun (moduleCodes: 101,1212,12,1212,1212,
        //将获取的菜单的code字符串转换为数组
        String[] strings = role.getModuleCodes().split(",");
        //保存关联数据
        for (String moduleCode : strings) {
            SysRoleFun sysRoleFun = new SysRoleFun();
            sysRoleFun.setModuleId(moduleCode);
            sysRoleFun.setRoleId(roleId+"");
            sysRoleFunMapper.insert(sysRoleFun);
        }

    }

    @Override
    public SysRole findRoleById(String roleCode) {
        return sysRoleMapper.selectByPrimaryKey(roleCode);
    }
}
