package com.example.controller;

import com.example.model.WxbCustomer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(Model model, WxbCustomer wxbCustomer){

        UsernamePasswordToken token = new UsernamePasswordToken(wxbCustomer.getLoginName(),wxbCustomer.getLoginPwd());
        try {

        SecurityUtils.getSubject().login(token);
        }catch (Exception e){
            return "login";

        }

        //跳转到 添加商品的页面前，可以针对展示的页面中，需要初始化的数据，先进行查询
        return "redirect:/index/index";
    }


}
