package com.example.controller;

import com.example.payUtil.HttpClient;
import com.example.payUtil.WeixinPayConfig;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping
public class MyWXPaytestController {

    @Autowired
    private WeixinPayConfig weixinPayConfig;

    @RequestMapping("createNative")
    public String createNative(Model model, String out_trade_no, String total_fee) {
        //1.参数封装
        //下面的参数可以通过封装一个实体类的bean对象，同时返回的结果，也可以封装的一个bean对象
        Map param = new HashMap();
        param.put("appid", weixinPayConfig.getApp_id());//公众账号ID
        param.put("mch_id", weixinPayConfig.getMch_id());//商户
        param.put("nonce_str", WXPayUtil.generateNonceStr());//随机字符串
        param.put("body", "无码人兽500部");
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

    /**
     * 根据订单号，关闭订单
     *
     * @param out_trade_no
     * @return
     */
    @RequestMapping("/closePay")
    @ResponseBody
    public Map closePay(String out_trade_no) {
        //1.封装参数
        Map param = new HashMap();
        param.put("appid", weixinPayConfig.getApp_id());
        param.put("mch_id", weixinPayConfig.getMch_id());
        param.put("out_trade_no", out_trade_no);
        param.put("nonce_str", WXPayUtil.generateNonceStr());
        try {
            String xmlParam = WXPayUtil.generateSignedXml(param, weixinPayConfig.getApi_key());
            //2.发送请求
            HttpClient httpClient = new HttpClient("https://api.mch.weixin.qq.com/pay/closeorder");
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
