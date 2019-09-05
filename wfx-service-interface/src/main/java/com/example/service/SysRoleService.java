package com.example.service;

import com.example.model.SysRole;

import java.util.List;

public interface SysRoleService {

    List<SysRole> findAll();

    void saveRole(SysRole role);

    SysRole findRoleById(String roleCode);
}
