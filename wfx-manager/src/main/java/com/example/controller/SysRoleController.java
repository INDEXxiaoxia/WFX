package com.example.controller;

import com.example.model.SysRole;
import com.example.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping("/findAll")
    public String findAll(Model model){
        /**
         * 在查询角色列表后，还需要将当前角色下的用户列表也希望查询出来
         *      1.需要在role对象中，添加一个 用户对象的集合类 属性
         *      2.在service层，将当前角色下的用户列表查询出来，并保存到 角色的 属性中
         *
         */
        List<SysRole> roleList = sysRoleService.findAll();

        model.addAttribute("roleList",roleList);

        return "admin-role";
    }
}
