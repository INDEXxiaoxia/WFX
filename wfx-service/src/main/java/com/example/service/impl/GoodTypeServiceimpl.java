package com.example.service.impl;

import com.example.mapper.GoodTypeMapper;
import com.example.model.WxbCustomer;
import com.example.model.WxbGoodType;
import com.example.service.GoodTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class GoodTypeServiceimpl  implements GoodTypeService {
    @Autowired
    GoodTypeMapper goodTypeMapper;

    @Override
    public List<WxbGoodType> findAll() {
        return goodTypeMapper.selectAll();
    }

    @Override
    public WxbGoodType selectById(String type_id) {
        return goodTypeMapper.selectByPrimaryKey(type_id);
    }
}
