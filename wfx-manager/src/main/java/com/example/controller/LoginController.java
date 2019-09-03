package com.example.controller;

import com.example.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/welcome")
    public String welcome() {
        return "welcome";
    }


    @RequestMapping("/login")
    public String login(UserInfo userInfo){

        return "index";
    }

}
