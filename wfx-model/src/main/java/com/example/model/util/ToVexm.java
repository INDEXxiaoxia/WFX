package com.example.model.util;

public class ToVexm {
    private String date;//订单号
    private int pricess; //价格
    private int buynum;//购买数量

    public ToVexm() {
        super();
    }

    public ToVexm(String date, int pricess, int buynum) {
        this.date = date;
        this.pricess = pricess;
        this.buynum = buynum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPricess() {
        return pricess;
    }

    public void setPricess(int pricess) {
        this.pricess = pricess;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }
}
