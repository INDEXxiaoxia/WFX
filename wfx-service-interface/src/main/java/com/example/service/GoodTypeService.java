package com.example.service;

import com.example.model.WxbCustomer;
import com.example.model.WxbGoodType;

import java.util.List;

public interface GoodTypeService {
    List<WxbGoodType> findAll();

    WxbGoodType selectById(String type_id);
}
