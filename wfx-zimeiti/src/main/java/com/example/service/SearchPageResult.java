package com.example.service;

import com.example.model.WxbGood;

import java.util.List;

public class SearchPageResult {
    private int pageP;//当前页数
    private int sizeP;//
    private int allWxbDatanumber;//总数据数
    private int allPageNumber;//页数
    private List<WxbGood> wxbGoods;
    private List<WxbGood> wxbGoodszone;

    public int getPageP() {
        return pageP;
    }

    public void setPageP(int pageP) {
        this.pageP = pageP;
    }

    public int getSizeP() {
        return sizeP;
    }

    public void setSizeP(int sizeP) {
        this.sizeP = sizeP;
    }

    public int getAllWxbDatanumber() {
        return allWxbDatanumber;
    }

    public void setAllWxbDatanumber(int allWxbDatanumber) {
        this.allWxbDatanumber = allWxbDatanumber;
    }

    public int getAllPageNumber() {
        return allPageNumber;
    }

    public void setAllPageNumber(int allPageNumber) {
        this.allPageNumber = allPageNumber;
    }

    public List<WxbGood> getWxbGoods() {
        return wxbGoods;
    }

    public void setWxbGoods(List<WxbGood> wxbGoods) {
        this.wxbGoods = wxbGoods;
    }

    public List<WxbGood> getWxbGoodszone() {
        return wxbGoodszone;
    }

    public void setWxbGoodszone(List<WxbGood> wxbGoodszone) {
        this.wxbGoodszone = wxbGoodszone;
    }
}
