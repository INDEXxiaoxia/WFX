package com.qianfeng.repeat.controller;

import com.qianfeng.repeat.mapper.VDeptEmpEmpMapper;
import com.qianfeng.repeat.model.VDeptEmpEmp;
import com.qianfeng.repeat.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;


    @Autowired
    private VDeptEmpEmpMapper deptEmpEmpMapper;


    @RequestMapping("/save")
    public void save() {
        deptService.saveMoreDept();
    }

    @RequestMapping("/findAllEmpEmp")
    public List<VDeptEmpEmp> findAll() {
        return deptEmpEmpMapper.selectAll();
    }
}
