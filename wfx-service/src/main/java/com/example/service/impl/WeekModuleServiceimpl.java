package com.example.service.impl;

import com.example.mapper.SysModuleMapper;
import com.example.model.SysModule;
import com.example.service.WeekModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class WeekModuleServiceimpl implements WeekModuleService {
    @Autowired
    SysModuleMapper sysModuleMapper;

    @Override
    public List<SysModule> findModuleByparm(String mname, String mid) {
       if (mname==null){
           mname="";
       }
        Example example = new Example(SysModule.class);
        example.and().andLike("moduleName", "%" + mname + "%");
        if (mid != "" && mid != null) {
            example.and().andEqualTo("moduleId", mid);
        }
        List<SysModule> sysModules = sysModuleMapper.selectByExample(example);
        return sysModules;
    }
}
