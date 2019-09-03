package com.example.mapper;

import com.example.model.UserInfo;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserInfoMapper extends Mapper<UserInfo> {

    @Select(value = "select * from  user_info i,sys_user_role ur where  i.user_id = ur.user_id and  ur.role_id = #{roleId}")
    public List<UserInfo> findListByRoleId(String roleId);
}
