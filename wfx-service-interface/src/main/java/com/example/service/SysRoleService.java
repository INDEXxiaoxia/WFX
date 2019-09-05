package com.example.service;

import com.example.model.SysRole;

import java.util.List;

public interface SysRoleService {
    /**
     * 查询所有角色列表
     * @return
     */
    List<SysRole> findAll();

    void saveRole(SysRole role);

    SysRole findRoleById(String roleCode);

    public List<SysRole> findRoleListByUserId(String userId);
}
