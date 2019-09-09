package com.example.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Data
public class WxbGoodAttribute {
    @Id
    private Long attrId;
    private Long catId;
    private String attrValues;
    private String attrName;
    private Long paixu;


}
