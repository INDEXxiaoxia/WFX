package com.example.controller;

import com.example.model.WxbMemeber;
import com.example.model.WxbOrder;
import com.example.service.WxbOrderService;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/o")
public class OrderController {
    @Autowired
    WxbOrderService wxbOrderService;
    @RequestMapping("/ol")
    public String ShowOrder(Model model,Integer pageO,String ispay){
        if (pageO==null)pageO=1;
        if(ispay==null) ispay="";
        if (ispay.equals("null")) ispay="";
        //查询  购物车  已支付
//        int pageO=1;
        int sizeO=5;
        WxbMemeber ThisMember = (WxbMemeber) SecurityUtils.getSubject().getPrincipal();
        //自媒体id  支付状态 页码
        PageInfo<WxbOrder> wxbOrderPageList=wxbOrderService.findorderListByuserId(ThisMember.getMemeberId(),ispay,pageO,sizeO);

        model.addAttribute("wxbOrderPageList",wxbOrderPageList);
        model.addAttribute("ispay",ispay);
        return "订单结算流水";
    }
    @RequestMapping("/del")
    public String DelOrder(Model model,String orderId ,String ispay){
        ispay="1";
        wxbOrderService.deleteOrderByOrderId(orderId);
        return "redirect:/o/ol?ispay="+ispay;

    }
}
