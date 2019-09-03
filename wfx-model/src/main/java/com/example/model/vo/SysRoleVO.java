package com.example.model.vo;

import com.example.model.UserInfo;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;

@Data
public class SysRoleVO {

    @Transient
    private List<UserInfo> userInfos;
    @Transient
    private String userInfosString;//专门用于前端展示数据时，变量
}
