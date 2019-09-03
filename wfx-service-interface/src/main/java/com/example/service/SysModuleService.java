package com.example.service;

import com.example.model.SysModule;
import com.example.model.util.ZTreeBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface SysModuleService {

    PageInfo<SysModule> findAll(int page, int rows);

    List<ZTreeBean> findAllMenus();

    List<ZTreeBean> findModuleByRoleId(String roleId);

    /**
     * 根据用户ID 查询当前用户能看到的菜单列表
     * @param userId
     * @return
     */
     List<SysModule> findModuleListByUserId(String userId);
}
