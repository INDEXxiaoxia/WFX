package com.example.controller;

import com.example.model.UserInfo;
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

    @RequestMapping("/initList")
    public String initList(Model model) {
        List<UserInfo> userInfoList = userInfoService.findListByParam();//执行不带条件查询操作
        model.addAttribute("userInfoList",userInfoList);
        return "admin-list";
    }

    //写 前端点击“查询按钮”时执行的查询操作
    @RequestMapping("/queryByparam")
    public String queryByparam(){

        return "admin-list";
    }







    @RequestMapping("/initAdd")
    public String initAdd() {

        return "admin-add";
    }


}
