package com.example.service;

import com.example.model.WxbGood;
import com.example.model.vo.Result;

import java.util.List;

public interface GoodService {

    boolean saveGood(WxbGood wxbGood);

    /**
     * 创建接口：根据条件查询商品列表
     * @param param
     * @return
     */
    List<WxbGood> findGoodByParam(WxbGood param);

    Result updateStateByGoodId(String goodId, String state);
}
