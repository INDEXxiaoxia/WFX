package com.example.controller;

import com.example.model.util.ZTreeBean;
import com.example.service.SysModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoZtreeController {
    @Autowired
    private SysModuleService sysModuleService;


    @RequestMapping("/init")
    public String initZtree() {
        return "demo/demo-ztree";
    }

    @RequestMapping("/findMenuList")
    @ResponseBody
    public List<ZTreeBean> findMenuList(String roleId) {
        return sysModuleService.findModuleByRoleId(roleId);
    }
}
