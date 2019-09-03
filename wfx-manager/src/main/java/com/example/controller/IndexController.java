package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.model.util.ZTreeBean;
import com.example.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/index")
@Controller
public class IndexController {
    @Autowired
    private SysModuleService sysModuleService;

//    @RequestMapping("/role")
//    public String indexRole() {
//        return "admin-role";
//    }

    @RequestMapping("/admin-role-add")
    public String adminRoleAdd(Model model) {
        List<ZTreeBean> treeBeans = sysModuleService.findModuleByRoleId(null);
        //在封装数据时，将集合对象，转换为json字符串
        model.addAttribute("treeBeans", JSONArray.toJSONString(treeBeans));
        return "admin-role-add";
    }

}
