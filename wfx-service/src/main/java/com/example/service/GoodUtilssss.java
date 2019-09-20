package com.example.service;

import com.example.mapper.GoodMapper;
import com.example.mapper.WxbOrderMapper;
import com.example.model.WxbGood;
import com.example.model.WxbOrder;
import com.example.model.util.GoodUtillll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodUtilssss {
  @Autowired
  WxbOrderMapper wxbOrderMapper;
  @Autowired
    GoodMapper goodMapper;
  //用于分拣good的sku
  public List<GoodUtillll> getSkuList(WxbGood wxbGood){
    List<GoodUtillll> goodSkuList=new ArrayList<>();
    if (wxbGood.getSkuTitle() != null && wxbGood.getSkuTitle() != ""&&wxbGood.getSkuTitle().length()!=0){
      String[] splitTitle = wxbGood.getSkuTitle().split("\\|");
      String[] splitCost = wxbGood.getSkuCost().split("\\|");
      String[] splitPrice = wxbGood.getSkuPrice().split("\\|");
      String[] splitPmoney = wxbGood.getSkuPmoney().split("\\|");
      for (int i = 0; i < splitTitle.length; i++) {
          goodSkuList.add(new GoodUtillll(i+1,splitTitle[i],Integer.parseInt(splitCost[i]),Integer.parseInt(splitPrice[i]),Integer.parseInt(splitPmoney[i])));
      }

    }
    return goodSkuList;
  }

  public Map<String,Integer> getOrderPriceMap(String orderId){
    Map<String,Integer> map= new HashMap<>();

    Example example=new Example(WxbOrder.class);
    example.and().andEqualTo("orderId",orderId);
    WxbOrder wxbOrder = wxbOrderMapper.selectOneByExample(example);
    GoodUtillll skuDict=new GoodUtillll();
    List<GoodUtillll> skuList = getSkuList(goodMapper.selectByPrimaryKey(wxbOrder.getGoodId()));
    for (GoodUtillll goodUtillll : skuList) {
      if (goodUtillll.getSkuId()==Integer.parseInt(wxbOrder.getSkuId())){
        skuDict=goodUtillll; break;
      }
    }
    map.put("skuCostZone", (int) (skuDict.getSkuCost()*wxbOrder.getBuyNum()));
    map.put("skuPriceZone", (int) (skuDict.getSkuPrice()*wxbOrder.getBuyNum()));
    map.put("skuPmoney", (int) (skuDict.getSkuPmoney()*wxbOrder.getBuyNum()));


    return map;
  }
}
