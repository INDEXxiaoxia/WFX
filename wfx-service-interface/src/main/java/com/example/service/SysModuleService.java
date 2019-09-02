package com.example.service;

import com.example.model.SysModule;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysModuleService {

    PageInfo<SysModule> findAll(int page, int rows);
}
