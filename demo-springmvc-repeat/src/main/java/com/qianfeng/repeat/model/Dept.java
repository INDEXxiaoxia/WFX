package com.qianfeng.repeat.model;


import javax.persistence.Id;
import javax.persistence.Table;

@Table
public class Dept {

    @Id
    private Long id;
    private String dname;
    private String loc;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getDname() {
        return dname;
    }

    public void setDname(String dname) {
        this.dname = dname;
    }


    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

}
