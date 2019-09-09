package com.example.model;


import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Data
public class WxbGoodAttr {
    @Id
    private Long goodsAttrId;
    private String goodsId;
    private Long attrId;
    private String attrValue;
    private String kftc;
    private String cb;
    private String jg;
    private String fc;
    private Long paixu;
    private String yh;

}
