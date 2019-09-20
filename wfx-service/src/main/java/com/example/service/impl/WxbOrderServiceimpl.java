package com.example.service.impl;

import com.example.mapper.WxbOrderMapper;
import com.example.model.WxbOrder;
import com.example.model.util.ToTubcUse;
import com.example.model.util.ToVexm;
import com.example.service.GoodUtilssss;
import com.example.service.WxbOrderService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;


@Transactional
@Service
public class WxbOrderServiceimpl implements WxbOrderService {
    @Autowired
    WxbOrderMapper wxbOrderMapper;
    @Autowired
    GoodUtilssss goodUtilssss;
    @Override
    public void saveOrder(WxbOrder wxbOrder) {
        wxbOrderMapper.insert(wxbOrder);
    }

    @Override
    public WxbOrder findByID(String orderId) {
        return wxbOrderMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public WxbOrder findByorderID(String orderId) {
        Example example=new Example(WxbOrder.class);
        example.and().andEqualTo("orderId",orderId);

        return wxbOrderMapper.selectOneByExample(example);
    }

    @Override
    public void changeOrderAlipayStateByOid(String oid) {
        WxbOrder wxbOrder = wxbOrderMapper.selectByPrimaryKey(oid);
        wxbOrder.setAlipayState("1");
        wxbOrderMapper.updateByPrimaryKey(wxbOrder);
    }

    @Override
    public PageInfo<WxbOrder> findorderListByuserId(String memeberId, String ispay, int pageO, int sizeO) {
        Example example=new Example(WxbOrder.class);
        example.and().andEqualTo("userId",memeberId);
        if (ispay!=""&&ispay!=null&&!ispay.equals("null")){
            example.and().andEqualTo("alipayState",ispay);
        }
        PageHelper.startPage(pageO,sizeO);
        List<WxbOrder> wxbOrderList = wxbOrderMapper.selectByExample(example);
        for (WxbOrder wxbOrder : wxbOrderList) {
            wxbOrder.setSkuMap(goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()));
        }
        return new PageInfo<>(wxbOrderList);
    }

    @Override
    public void deleteOrderByOrderId(String orderId) {
        Example example=new Example(WxbOrder.class);
        example.and().andEqualTo("orderId",orderId);
        wxbOrderMapper.deleteByExample(example);
    }

    @Override
    public ToTubcUse findTubcPar(String memeberId) {
        ToTubcUse toTubcUse=new ToTubcUse();
        Example example=new Example(WxbOrder.class);
        example.and().andEqualTo("userId",memeberId);
        example.and().andEqualTo("alipayState",'1');
        //已支付订单
        List<WxbOrder> wxbOrderList = wxbOrderMapper.selectByExample(example);
        toTubcUse.setPayOrderNum(wxbOrderList.size());
        int PayOrderPrice=0;int PayOrderPmoney=0;
        for (WxbOrder wxbOrder : wxbOrderList) {
            PayOrderPrice+=goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()).get("skuPriceZone");
            PayOrderPmoney+=goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()).get("skuPmoney");
        }
        toTubcUse.setPayOrderPrice(PayOrderPrice);
        toTubcUse.setPayOrderPmoney(PayOrderPmoney);
        //------------------------------------未支付
        example.clear();
        example.and().andEqualTo("userId",memeberId);
        example.and().andEqualTo("alipayState",'0');
        //未支付订单
        List<WxbOrder> wxbOrderListNO= wxbOrderMapper.selectByExample(example);


        toTubcUse.setNotPayOrderNum(wxbOrderListNO.size());
        int PayOrderPriceNO=0;int PayOrderPmoneyNO=0;
        for (WxbOrder wxbOrder : wxbOrderListNO) {
            PayOrderPriceNO+=goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()).get("skuPriceZone");
            PayOrderPmoneyNO+=goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()).get("skuPmoney");
        }
        toTubcUse.setNotPayOrderPrice(PayOrderPriceNO);
        toTubcUse.setNotPayOrderPmoney(PayOrderPmoneyNO);
        return toTubcUse;
    }

    @Override
    public List<ToVexm> findToxmList(String memeberId) {
        ToTubcUse toTubcUse=new ToTubcUse();
        List<ToVexm> toVexmList=new ArrayList<>();
        Example example=new Example(WxbOrder.class);
        example.and().andEqualTo("userId",memeberId);
        List<WxbOrder> wxbOrderList = wxbOrderMapper.selectByExample(example);
        for (WxbOrder wxbOrder : wxbOrderList) {
            toVexmList.add(
                    new ToVexm(wxbOrder.getOrderId(),
                            goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()).get("skuPriceZone"),
                            goodUtilssss.getOrderPriceMap(wxbOrder.getOrderId()).get("skuPmoney"))
            );
        }
        return toVexmList;
    }
}
