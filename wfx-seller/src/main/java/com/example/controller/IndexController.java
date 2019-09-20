package com.example.controller;

import com.example.model.WxbCustomer;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/index")
public class IndexController {
    @RequestMapping("/index")
    public String IndexView(Model model){
        WxbCustomer ThisCustomer = (WxbCustomer) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("ThisCustomer",ThisCustomer);
        return "index";
    }

}
