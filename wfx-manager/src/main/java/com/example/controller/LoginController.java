package com.example.controller;

import com.example.model.SysModule;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {

    @RequestMapping("/testWfx")
    public String testWfx(HttpServletRequest request,String userName,String password){

        return null;
    }
}
