package com.example.controller;

import com.example.model.WxbGood;
import com.example.model.WxbMemeber;
import com.example.model.WxbOrder;
import com.example.model.util.GoodUtillll;
import com.example.payUtil.HttpClient;
import com.example.payUtil.WeixinPayConfig;
import com.example.service.*;
import com.example.service.GoodUtilssss;
import com.github.wxpay.sdk.WXPayUtil;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
//    buy/xq  商品详情  包含 下单按钮  显示商品详情

@Controller
@RequestMapping("/buy")
public class GoodBuyController {
    @Autowired
    WxbMemberService memberService;
    @Autowired
    GoodService goodService;
    @Autowired
    GoodUtilssss goodUtilssss;
    @Autowired
    WxbOrderService wxbOrderService;
    @Autowired
    OrderPayUtil orderPayUtil;
    @Autowired
    WxbOrderService orderService;
    @Autowired
    private WeixinPayConfig weixinPayConfig;
    @RequestMapping("/xq")
    public String goodXQviews(Model model, String goodId){
        //获取good详情
        WxbGood wxbGood=goodService.selectBygoodId(goodId);
        //获取shiro中的用户信息
        WxbMemeber ThisMember = (WxbMemeber) SecurityUtils.getSubject().getPrincipal();
        List<GoodUtillll> skuList = goodUtilssss.getSkuList(wxbGood);

        model.addAttribute("wxbGood",wxbGood);
        model.addAttribute("ThisMember",ThisMember);
        model.addAttribute("skuList",skuList);
        return "产品及文案-详情";
    }
    @RequestMapping("/xd")
    @ResponseBody
    public String buybuy(String goodId,String skuId,Long buyNum,String address,String orderRemark){
        // 将订单存储为未支付状态
        // 前端参数：商品ID 套餐号 购买数量 地区 备注
        WxbMemeber ThisMemeber=(WxbMemeber)SecurityUtils.getSubject().getPrincipal();
        /***
         * 订单号：orderId
         * 买家手机号：buyerPhone
         * 商品ID：goodId
         * 下单时间：orderTime
         * 购买数量：buyNum
         * 买家姓名：buyerName
         * 套餐ID：skuId
         * 下单者ID userId;
         * 详细地址：address
         * 备注信息：orderRemark
         * 0 正常下单 1 代客录单 2 异常订单：ordertype
         * 最后修改时间：updateTime
         * 支付状态：alipayState  0未支付 1支付
         */
        WxbOrder wxbOrder=new WxbOrder();
        String orderId=UUID.randomUUID().toString();
        wxbOrder.setOrderId(orderId);
        wxbOrder.setBuyerPhone(ThisMemeber.getPhone()) ;
        wxbOrder.setGoodId(goodId);
        wxbOrder.setOrderTime(new Date());
        wxbOrder.setBuyNum(buyNum);
        wxbOrder.setBuyerName(ThisMemeber.getName());
        wxbOrder.setSkuId(skuId);
        wxbOrder.setUserId(ThisMemeber.getMemeberId());
        wxbOrder.setAddress(address);
        wxbOrder.setOrderRemark(orderRemark);
        wxbOrder.setOrderType(1L);
        wxbOrder.setUpdateTime(new Date());
        wxbOrder.setAlipayState("0");

        wxbOrderService.saveOrder(wxbOrder);
//        try {
//
//        }catch (Exception e){
//
//        }

        return orderId;
    }


    @RequestMapping("/pay")
    public String paypay(Model model,String orderId){
        Map<String, Integer> orderPrice = orderPayUtil.getOrderPrice(orderId);
        WxbOrder wxbOrder=orderService.findByorderID(orderId);
        WxbGood wxbGood=goodService.selectBygoodId(wxbOrder.getGoodId());
        String GoodTitleDesc=wxbGood.getGoodName()+""+wxbOrder.getBuyNum()+"个";
        String out_trade_no=wxbOrder.getOid()+"QQQXx1";
        String  total_fee=orderPrice.get("skuPriceZone")+"";
        model.addAttribute("wxbOrder",wxbOrder);
        model.addAttribute("wxbGood",wxbGood);
        model.addAttribute("orderPrice",orderPrice);
        //------------------------------------------------------------------

        //1.参数封装
        //下面的参数可以通过封装一个实体类的bean对象，同时返回的结果，也可以封装的一个bean对象
        Map param = new HashMap();
        param.put("appid", weixinPayConfig.getApp_id());//公众账号ID
        param.put("mch_id", weixinPayConfig.getMch_id());//商户
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body", GoodTitleDesc);
        param.put("out_trade_no", out_trade_no);//交易订单号（是由系统内部来生成）
        param.put("total_fee", total_fee);//金额（分）
        param.put("spbill_create_ip", "127.0.0.1");
        param.put("notify_url", "http://www.baidu.com");
        param.put("trade_type", "NATIVE");//交易类型
        System.out.println(this.getClass().getName() + "==================方法：createNative===== 参数map的值： " + param.toString());
        try {
            //根据appkey来生成签名，并同时将参数进行转换为xml格式
            String xmlParam = WXPayUtil.generateSignedXml(param, weixinPayConfig.getApi_key());
            System.out.println("请求的参数：" + xmlParam);

            //2.发送请求（按照官方接口要求，将数据以post请求方式发送）
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();

            //3.获取结果
            String xmlResult = httpClient.getContent();

            Map<String, String> mapResult = WXPayUtil.xmlToMap(xmlResult);
            System.out.println("微信返回结果" + mapResult);


            //如果请求 下单接口操作成功，则微信支付接口方，会返回一个  扫描支付的支付地址
            model.addAttribute("code_url", mapResult.get("code_url"));//生成支付二维码的链接
            model.addAttribute("out_trade_no", out_trade_no);
            model.addAttribute("total_fee", total_fee);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return "weixin_pay";
        //------------------------------------------------------------------

    }
    @RequestMapping("/finished")
    @ResponseBody
    public String finishPay(String trueOid){
        Map map = orderPayUtil.queryPayStatus(trueOid);
        String  trade_state= (String) map.get("trade_state");
        if (trade_state.equals("SUCCESS")){
            String oid=trueOid.split("QQQ")[0];
            orderService.changeOrderAlipayStateByOid(oid);
        }
        return trade_state;
    }

    @RequestMapping("/finishedtest")
    @ResponseBody
    public Map seeOrder(String trueOid){
        Map map = orderPayUtil.queryPayStatus(trueOid);
        return map;
    }

}
