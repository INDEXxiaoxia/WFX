package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/index")
@Controller
public class IndexController {


    @RequestMapping("/role")
    public String indexRole(){
        return "admin-role";
    }

}
