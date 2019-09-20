package com.example.controller;

import com.example.Dao.MyWxbGoodRepository;
import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/test")
public class Utilssss {
    @Autowired
    MyWxbGoodRepository myWxbGoodRepository;
    @Autowired
    GoodMapper goodMapper;

    //向es中插入原始数据
    public void importDataToEs() {
        List<WxbGood> wxbGoods = goodMapper.selectAll();
        for (WxbGood wxbGood : wxbGoods) {
            myWxbGoodRepository.save(wxbGood);
        }
    }
    public List<WxbGood> findGLByCidTidGnameOrderByStp(String customer_id, String type_id, String sort_type, String alisaname) {
        List<WxbGood> wxbGoodList = myWxbGoodRepository.findByCustomerIdAndTypeIdAndGoodNameLikeOrderByToped(customer_id, type_id, alisaname);


        return wxbGoodList;
    }
    @RequestMapping("/TestES")
    @ResponseBody
    public List<WxbGood> Testsss(){
        return findGLByCidTidGnameOrderByStp("qf283942", "01", "", "");
    }

}
