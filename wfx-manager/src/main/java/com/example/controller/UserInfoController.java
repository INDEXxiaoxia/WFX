package com.example.controller;

import com.example.model.SysRole;
import com.example.model.UserInfo;
import com.example.model.vo.Result;
import com.example.service.SysRoleService;
import com.example.service.UserInfoService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
        model.addAttribute("userInfoList", userInfoList);
        return "admin-list";
    }

    //写 前端点击“查询按钮”时执行的查询操作
    @RequestMapping("/queryByparam")
    public String queryByparam(UserInfo userInfo, Model model) {
        List<UserInfo> userInfoList = userInfoService.findListByParam(userInfo);//执行不带条件查询操作
        //将查询的条件再次传回，实现条件回显
        model.addAttribute("userInfo", userInfo);
        model.addAttribute("userInfoList", userInfoList);
        return "admin-list";
    }

    @RequestMapping("/initAdd")
    @RequiresRoles(value = {"admin","user"})
    public String initAdd(Model model) {
        //进入到新增页面时，需要将所有角色列表查询出来，返回给界面
        List<SysRole> roleList = sysRoleService.findAll();
        model.addAttribute("roleList", roleList);
        return "admin-add";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(@RequestBody UserInfo userInfo) {
        /*
            1.控制层接收到当前要添加的用户信息后，在保存到数据库时，是经过下面几步的操作：
                1）将用户信息保存到 user_info表
                2)将用户-角色的关系保存到 sys_user_role表
                3)将当前添加的用户密码，进行shiro格式的加密
         */
        System.out.println("=============== userinfo:" + userInfo.toString());

        try {
            userInfoService.save(userInfo);
            return new Result(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, "操作失败");
        }

    }

}
