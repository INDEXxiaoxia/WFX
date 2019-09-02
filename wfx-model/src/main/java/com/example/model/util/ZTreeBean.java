package com.example.model.util;
//{id:1, pId:0, name:"[core] 基本功能 演示", open:true},

import lombok.Data;

@Data
public class ZTreeBean {
    private String id;
    private String pId;
    private String name;
    private Boolean open;
    private Boolean checked;//true:选中，false:不选中

}
