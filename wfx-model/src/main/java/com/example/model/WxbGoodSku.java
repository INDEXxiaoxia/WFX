package com.example.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;

@Table
@Data
public class WxbGoodSku {
    @Id
    private String skuId;
    private String skuName;
    private String skuCost;
    private String skuPrice;
    private String skuPmoney;
    private String goodId;
    private Long orderNo;
    private String serviceMoney;
}
