package com.example.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Data
public class SysRoleFun {
    @Id
    private Long funId;
    private String moduleId;
    private String roleId;


}
