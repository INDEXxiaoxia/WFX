package com.example.controller;

import com.example.model.SysRole;
import com.example.model.UserInfo;
import com.example.service.SysRoleService;
import com.example.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/initList")
    public String initList(Model model) {
        List<UserInfo> userInfoList = userInfoService.findListByParam(null);//执行不带条件查询操作
        model.addAttribute("userInfoList",userInfoList);
        return "admin-list";
    }

    //写 前端点击“查询按钮”时执行的查询操作
    @RequestMapping("/queryByparam")
    public String queryByparam(UserInfo userInfo,Model model){
        List<UserInfo> userInfoList = userInfoService.findListByParam(userInfo);//执行不带条件查询操作
        //将查询的条件再次传回，实现条件回显
        model.addAttribute("userInfo",userInfo);
        model.addAttribute("userInfoList",userInfoList);
        return "admin-list";
    }



    @RequestMapping("/initAdd")
    public String initAdd(Model model) {
        //进入到新增页面时，需要将所有角色列表查询出来，返回给界面
        List<SysRole> roleList = sysRoleService.findAll();
        model.addAttribute("roleList",roleList);
        return "admin-add";
    }


}
