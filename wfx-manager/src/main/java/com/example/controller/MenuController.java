package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/menu")
public class MenuController {


    @RequestMapping("/initMenu")
    public String initMenu(){
        return "menu-list";
    }
}
