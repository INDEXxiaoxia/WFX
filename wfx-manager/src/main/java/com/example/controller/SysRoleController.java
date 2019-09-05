package com.example.controller;

import com.alibaba.fastjson.JSONArray;
import com.example.model.SysRole;
import com.example.model.util.ZTreeBean;
import com.example.model.vo.Result;
import com.example.service.SysModuleService;
import com.example.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysModuleService sysModuleService;

    @RequestMapping("/findAll")
    public String findAll(Model model) {
        /**
         * 在查询角色列表后，还需要将当前角色下的用户列表也希望查询出来
         *      1.需要在role对象中，添加一个 用户对象的集合类 属性
         *      2.在service层，将当前角色下的用户列表查询出来，并保存到 角色的 属性中
         *
         */
        List<SysRole> roleList = sysRoleService.findAll();
        model.addAttribute("roleList", roleList);
        return "admin-role";
    }

    @RequestMapping("/save")
    @ResponseBody
    public Result save(@RequestBody SysRole role) {//当是异步请求时，封装一个结果集
        //将参数保存到数据库
        sysRoleService.saveRole(role);
        return new Result(true,"操作成功");
    }

    @RequestMapping("/findRoleById")
    public String findRoleById(Model model,String roleCode){
        //根据角色编号查询角色信息
        SysRole sysRole = sysRoleService.findRoleById(roleCode);
        List<ZTreeBean> treeBeans = sysModuleService.findModuleByRoleId(roleCode);
        model.addAttribute("sysRole",sysRole);
        model.addAttribute("treeBeans", JSONArray.toJSONString(treeBeans));
        return "admin-role-add";
    }
}
