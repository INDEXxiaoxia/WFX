package com.example.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table
public class WxbCustomer implements Serializable {

    @Id
    private String customerId;
    private String customerName;
    private String qq;
    private String wxh;
    private String phone;
    private java.util.Date createtime;
    private String loginName;
    private String loginPwd;
    private Long state;
    private Long level;
    private Double accBalance;
    private java.util.Date updateTime;
    private String website;

}
