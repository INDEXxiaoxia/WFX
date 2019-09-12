package com.qianfeng.repeat.controller;

import com.qianfeng.repeat.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/save")
    public void save() {
        deptService.saveMoreDept();
    }
}
