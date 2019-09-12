package com.example.repeat.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class DemoController {

    @RequestMapping("/rest")
    public String restInit(String userName, HttpServletRequest request){
        return "欢迎来到springboot的世界";
    }
}
