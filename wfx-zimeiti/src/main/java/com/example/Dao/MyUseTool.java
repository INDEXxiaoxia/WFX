package com.example.Dao;



import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class MyUseTool {
    public MyWxbGoodRepository myWxbGoodRepository;
//    @Autowired
////    GoodMapper goodMapper;
////
////    //向es中插入原始数据
////    public void importDataToEs(){
////        List<WxbGood> wxbGoods = goodMapper.selectAll();
////        for (WxbGood wxbGood : wxbGoods) {
////            myWxbGoodRepository.save(wxbGood);
////        }
////    }
    public List<WxbGood> findGLByCidTidGnameOrderByStp(String customer_id, String type_id, String sort_type, String alisaname){
        List<WxbGood> wxbGoodList=myWxbGoodRepository.findByCustomerIdAndTypeIdAndGoodNameLikeOrderByToped(customer_id,type_id,alisaname);
        return wxbGoodList;
    }

}
