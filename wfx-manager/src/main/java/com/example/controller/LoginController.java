package com.example.controller;

import com.example.model.SysModule;
import com.example.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private SysModuleService sysModuleService;

//    @RequestMapping("/index")
//    @ResponseBody
//    public List<SysModule> index() {
//        String userId = "28486868";
//        //假设当前登录的用户的user_id = 23946449
//
//        //1.根据当前登录的用户的用户ID查询当前用户能看到的菜单列表
//        List<SysModule> moduleList = sysModuleService.findModuleListByUserId(userId);
//        //2.遍历菜单列表，动态展示菜单数据(已经是当前用户能看的到的菜单）
//        return moduleList;
//
//
////        return "index";
//    }

    @RequestMapping("/index")
    @ResponseBody
    public List<SysModule> index() {
        String userId = "23946449";
        //假设当前登录的用户的user_id = 23946449

        //1.根据当前登录的用户的用户ID查询当前用户能看到的菜单列表
        List<SysModule> moduleList = sysModuleService.findModuleListByUserId(userId);
        //2.遍历菜单列表，动态展示菜单数据(已经是当前用户能看的到的菜单）
        return moduleList;


//        return "index";
    }
    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }
}
