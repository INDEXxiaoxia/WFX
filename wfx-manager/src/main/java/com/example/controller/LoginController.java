package com.example.controller;

import com.example.model.SysModule;
import com.example.model.UserInfo;
import com.example.service.SysModuleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private SysModuleService sysModuleService;

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }

//    @RequestMapping("/index")
//    public String initIndex(){
//        return "index";
//    }
    @RequestMapping("/login")
    public String login(Model model,UserInfo userInfo) {

        UsernamePasswordToken token = new UsernamePasswordToken(userInfo.getAccount(), userInfo.getPassword());
        try {
            SecurityUtils.getSubject().login(token);//这一行操作成功，则表示用户登录成功


            //这里来查询首页需要展示的菜单列表
//            String userId = "23946448";

            UserInfo userInfo1 = (UserInfo) SecurityUtils.getSubject().getPrincipal();
            //1.根据当前登录的用户的用户ID查询当前用户能看到的菜单列表
            List<SysModule> moduleList = sysModuleService.findModuleListByUserId(userInfo1.getUserId());
            model.addAttribute("moduleList",moduleList);//将查询的菜单列表返回给界面
            return "index";
        }catch (Exception e){
            return "redirect:/welcome";
        }

    }

    @RequestMapping("/logout")
    public String logout() {
        SecurityUtils.getSubject().logout();
        //退出操作成功后，做了重定向的操作访问是一个 完全不存在的请求。
        return "redirect:/welcome";//这个重定向的请求可以写任何url，但是不能写被shiro进行了anon的请求
    }

//    @RequestMapping("/index")
//    @ResponseBody
//    public List<SysModule> index() {
//        String userId = "23946448";
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
}
