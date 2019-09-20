package com.example.service;

import com.example.model.WxbOrder;
import com.example.model.util.ToTubcUse;
import com.example.model.util.ToVexm;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface WxbOrderService {
    void saveOrder(WxbOrder wxbOrder);

    WxbOrder findByID(String orderId);

    WxbOrder findByorderID(String orderId);

    void changeOrderAlipayStateByOid(String oid);

    PageInfo<WxbOrder> findorderListByuserId(String memeberId, String ispay, int pageO, int sizeO);


    void deleteOrderByOrderId(String orderId);

    ToTubcUse findTubcPar(String memeberId);

    List<ToVexm> findToxmList(String memeberId);
}
