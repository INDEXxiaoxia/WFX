package com.example.service.impl;

import com.example.mapper.SysModuleMapper;
import com.example.model.SysModule;
import com.example.service.SysModuleService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SysModuleServiceImpl implements SysModuleService {

    @Autowired
    private SysModuleMapper sysModuleMapper;

    @Override
    public PageInfo<SysModule> findAll(int page, int rows) {
        PageHelper.startPage(page,rows);
        return new PageInfo<SysModule>(sysModuleMapper.selectAll());
    }
}
