package com.qianfeng.repeat.service.impl;

import com.qianfeng.repeat.mapper.DeptMapper;
import com.qianfeng.repeat.model.Dept;
import com.qianfeng.repeat.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
//@Transactional
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptMapper deptMapper;

    @Override
    public void saveMoreDept() {
        //先添加一条数据
        Dept dept1 = new Dept();
        dept1.setId(190411L);
        dept1.setDname("1904班级");
        dept1.setLoc("1904是一个大班");
        deptMapper.insert(dept1);

        int i = 1 / 0;

        //先添加一条数据
        Dept dept2 = new Dept();
        dept2.setId(130411L);
        dept2.setDname("dept2");
        dept2.setLoc("dept2");
        deptMapper.insert(dept2);

    }
}
