package com.example.model;


import com.example.model.vo.SysRoleVO;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Data
@Table
public class SysRole extends SysRoleVO {
    @Id
    private String roleCode;
    private String roleName;
    private String roleDesc;
    private Long roleOrder;
    private Long roleType;

    @Transient
    private String moduleCodes;//接收到的所有关联的菜单ID
}
