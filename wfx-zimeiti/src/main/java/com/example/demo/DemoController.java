package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class DemoController {


    @RequestMapping("/init")
    public String init(){
        return "index";
    }
}
