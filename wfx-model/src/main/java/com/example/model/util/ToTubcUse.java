package com.example.model.util;

public class ToTubcUse {
    private Integer payOrderNum;
    private Integer payOrderPrice;
    private Integer payOrderPmoney;
    private Integer notPayOrderNum;
    private Integer notPayOrderPrice;
    private Integer notPayOrderPmoney;

    public Integer getPayOrderNum() {
        return payOrderNum;
    }

    public void setPayOrderNum(Integer payOrderNum) {
        this.payOrderNum = payOrderNum;
    }

    public Integer getPayOrderPrice() {
        return payOrderPrice;
    }

    public void setPayOrderPrice(Integer payOrderPrice) {
        this.payOrderPrice = payOrderPrice;
    }

    public Integer getPayOrderPmoney() {
        return payOrderPmoney;
    }

    public void setPayOrderPmoney(Integer payOrderPmoney) {
        this.payOrderPmoney = payOrderPmoney;
    }

    public Integer getNotPayOrderNum() {
        return notPayOrderNum;
    }

    public void setNotPayOrderNum(Integer notPayOrderNum) {
        this.notPayOrderNum = notPayOrderNum;
    }

    public Integer getNotPayOrderPrice() {
        return notPayOrderPrice;
    }

    public void setNotPayOrderPrice(Integer notPayOrderPrice) {
        this.notPayOrderPrice = notPayOrderPrice;
    }

    public Integer getNotPayOrderPmoney() {
        return notPayOrderPmoney;
    }

    public void setNotPayOrderPmoney(Integer notPayOrderPmoney) {
        this.notPayOrderPmoney = notPayOrderPmoney;
    }

    @Override
    public String toString() {
        return "ToTubcUse{" +
                "payOrderNum=" + payOrderNum +
                ", payOrderPrice=" + payOrderPrice +
                ", payOrderPmoney=" + payOrderPmoney +
                ", notPayOrderNum=" + notPayOrderNum +
                ", notPayOrderPrice=" + notPayOrderPrice +
                ", notPayOrderPmoney=" + notPayOrderPmoney +
                '}';
    }
}
