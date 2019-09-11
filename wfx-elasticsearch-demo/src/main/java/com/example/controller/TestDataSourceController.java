package com.example.controller;

import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestDataSourceController {

    @Autowired
    private GoodMapper goodMapper;

    @RequestMapping("/findAll")
    @ResponseBody
    public List<WxbGood> findAll(){
        List<WxbGood> wxbGoods = goodMapper.selectAll();

        return wxbGoods;
    }
}
