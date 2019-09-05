package com.example.mapper;

import com.example.model.SysRole;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface SysRoleMapper extends Mapper<SysRole> {


    @Select(value = "select r.* from sys_role r,sys_user_role ur where r.role_code = ur.role_id and  ur.user_id = #{userId}")
    public List<SysRole> findRoleListByUserId(String userId);
}
