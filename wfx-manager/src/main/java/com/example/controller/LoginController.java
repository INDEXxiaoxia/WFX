package com.example.controller;

import com.example.model.SysModule;
import com.example.model.UserInfo;
import com.example.service.SysModuleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


    @RequestMapping("/login")
    public String login(UserInfo userInfo) {
        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getAccount(), userInfo.getPassword());
        SecurityUtils.getSubject().login(token);
        return "index";
    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        return "redirect:/welcome";
    }

    @Autowired
    private SysModuleService sysModuleService;

    @RequestMapping("/index")
    @ResponseBody
    public List<SysModule> index() {
        String userId = "28486868";
        //假设当前登录的用户的user_id = 23946449

        //1.根据当前登录的用户的用户ID查询当前用户能看到的菜单列表
        List<SysModule> moduleList = sysModuleService.findModuleListByUserId(userId);
        //2.遍历菜单列表，动态展示菜单数据(已经是当前用户能看的到的菜单）
        return moduleList;


//        return "index";
    }
}
