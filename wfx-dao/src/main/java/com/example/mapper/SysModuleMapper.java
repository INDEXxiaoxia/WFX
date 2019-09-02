package com.example.mapper;

import com.example.model.SysModule;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

//自定义接口，继续tkmapper的接口即可
public interface SysModuleMapper extends Mapper<SysModule> {
//查询所有----接口中已经存在selectAll

    //根据角色ID查询角色下所有的菜单权限列表
    @Select(value = "SELECT m.* from sys_module m , sys_role_fun f WHERE m.module_code = f.module_id and f.role_id = #{roleId}")
    public List<SysModule> findModuleByRoleId(String roleId);
}
