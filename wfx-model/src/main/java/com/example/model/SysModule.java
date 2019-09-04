package com.example.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

/*
    在对实体类进行管理时，可以借助一个插件 lombock 简化实体类中所有的getter和setter以及toString

    使用lombok插件，分两步：
        1、导入坐标
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </dependency>
         2.配置idea的lombok插件
            settings ---- plugins ----- 搜索 lombok插件
 */
@Data
@Table
public class SysModule {
    @Id
    private String moduleId;
    private String moduleCode;
    private String moduleName;
    private String linkUrl;
    private Long moduleOrder;
    private String parentModule;
    private String moduleDesc;
    private String expanded;
    private String leaf;

    @Transient
    private List<SysModule> childModules;

}
