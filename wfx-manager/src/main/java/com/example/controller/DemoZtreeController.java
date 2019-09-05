package com.example.controller;

import com.example.model.SysRole;
import com.example.model.util.ZTreeBean;
import com.example.service.SysModuleService;
import com.example.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/demo")
public class DemoZtreeController {
    @Autowired
    private SysModuleService sysModuleService;

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/init")
    public String initZtree() {
        return "demo/demo-ztree2";
    }

    @RequestMapping("/findMenuList")
    @ResponseBody
    public List<ZTreeBean> findMenuList(String roleId) {
        return sysModuleService.findModuleByRoleId(roleId);
    }

    @RequestMapping("/selectRole")
    public String selectRole(Model model){
        List<SysRole> sysRoleList = sysRoleService.findAll();
        model.addAttribute("sysRoleList",sysRoleList);

        return "demo/demo-select";
    }
}
