package com.example.service;

import com.example.mapper.GoodMapper;
import com.example.mapper.WxbOrderMapper;
import com.example.model.WxbOrder;
import com.example.model.util.GoodUtillll;
import com.example.payUtil.HttpClient;
import com.example.payUtil.WeixinPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderPayUtil {
    @Autowired
    WxbOrderMapper wxbOrderMapper;
    @Autowired
    GoodUtilssss goodUtilssss;
    @Autowired
    GoodMapper goodMapper;
    @Autowired
    private WeixinPayConfig weixinPayConfig;
        //通过订单号获取该订单的所有 价格信息
    public Map<String,Integer> getOrderPrice(String orderId){
        Map<String,Integer> map= new HashMap<>();

        Example example=new Example(WxbOrder.class);
        example.and().andEqualTo("orderId",orderId);
        WxbOrder wxbOrder = wxbOrderMapper.selectOneByExample(example);
        GoodUtillll skuDict=new GoodUtillll();
        List<GoodUtillll> skuList = goodUtilssss.getSkuList(goodMapper.selectByPrimaryKey(wxbOrder.getGoodId()));
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


    /**
     * 根据订单号，查询订单的状态
     *
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/queryPayStatus")
    @ResponseBody
    public Map queryPayStatus(String out_trade_no) {
        //1.封装参数
        Map param = new HashMap();
        param.put("appid", weixinPayConfig.getApp_id());
        param.put("mch_id", weixinPayConfig.getMch_id());
        param.put("out_trade_no", out_trade_no);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, weixinPayConfig.getApi_key());
            //2.发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/orderquery");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //3.获取结果
            String xmlResult = httpClient.getContent();
            Map<String, String> map = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("调动查询API返回结果：" + xmlResult);

            return map;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

    }



}
