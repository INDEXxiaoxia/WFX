package com.example.model.util;

import org.springframework.stereotype.Service;

public class GoodUtillll {
    private Integer skuId;
    private String skuTitle;//描述
    private Integer skuCost;//成本
    private Integer skuPrice;//价格
    private Integer skuPmoney;//分成

    @Override
    public String toString() {
        return "GoodUtillll{" +
                "skuId=" + skuId +
                ", skuTitle='" + skuTitle + '\'' +
                ", skuCost=" + skuCost +
                ", skuPrice=" + skuPrice +
                ", skuPmoney=" + skuPmoney +
                '}';
    }

    public GoodUtillll() {
        super();
    }

    public GoodUtillll(Integer skuId, String skuTitle, Integer skuCost, Integer skuPrice, Integer skuPmoney) {
        this.skuId = skuId;
        this.skuTitle = skuTitle;
        this.skuCost = skuCost;
        this.skuPrice = skuPrice;
        this.skuPmoney = skuPmoney;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public void setSkuId(Integer skuId) {
        this.skuId = skuId;
    }

    public String getSkuTitle() {
        return skuTitle;
    }

    public void setSkuTitle(String skuTitle) {
        this.skuTitle = skuTitle;
    }

    public Integer getSkuCost() {
        return skuCost;
    }

    public void setSkuCost(Integer skuCost) {
        this.skuCost = skuCost;
    }

    public Integer getSkuPrice() {
        return skuPrice;
    }

    public void setSkuPrice(Integer skuPrice) {
        this.skuPrice = skuPrice;
    }

    public Integer getSkuPmoney() {
        return skuPmoney;
    }

    public void setSkuPmoney(Integer skuPmoney) {
        this.skuPmoney = skuPmoney;
    }
}
