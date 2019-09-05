package com.example.controller;

import com.example.model.WxbCustomer;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

    @RequestMapping("/login")
    public String login(WxbCustomer wxbCustomer){

        UsernamePasswordToken token = new UsernamePasswordToken(wxbCustomer.getLoginName(),wxbCustomer.getLoginPwd());
        SecurityUtils.getSubject().login(token);

        return "goods-add";
    }


}
