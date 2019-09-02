package com.example.controller;

import com.example.model.SysModule;
import com.example.service.SysModuleService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/module")
public class SysModuleController {

    @Autowired
    private SysModuleService sysModuleService;

    @RequestMapping("/findPage")
    public String findPage(Model model, int page, int rows) {
        PageInfo<SysModule> pageInfo = sysModuleService.findAll(page, rows);
        model.addAttribute("pageInfo",pageInfo);
        return "sys-module-list";
    }

}
