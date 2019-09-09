package com.example.controller;

import com.example.model.WxbGood;
import com.example.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodService goodService;

    @RequestMapping("/initGood")
    public String initGood(Model model){

        List<WxbGood> wxbGoods = goodService.findGoodByParam(null);

        //将查询的数据封装到model中，并返回给界面
        model.addAttribute("goods",wxbGoods);
        return "product-list";
    }

    //添加查询方法
    @RequestMapping("/findGoodsByParam")
    public String findGoodsByParam(Model model, WxbGood param){
        List<WxbGood> wxbGoods = goodService.findGoodByParam(param);

        //将查询的数据封装到model中，并返回给界面
        model.addAttribute("goods",wxbGoods);
        //将查询的条件再次带回页面
        model.addAttribute("param",param);
        return "product-list";
    }
}
