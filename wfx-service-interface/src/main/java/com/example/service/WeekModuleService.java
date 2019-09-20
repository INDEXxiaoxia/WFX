package com.example.service;

import com.example.model.SysModule;

import java.util.List;

public interface WeekModuleService {
    List<SysModule> findModuleByparm(String mname, String mid);
}
