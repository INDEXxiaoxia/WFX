package com.example.service.impl;


import com.example.mapper.CustomerMapper;
import com.example.mapper.GoodMapper;
import com.example.model.WxbCustomer;
import com.example.model.WxbGood;
import com.example.service.CustomerService;
import com.example.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class CustomerServiceimpl implements CustomerService {
    @Autowired
    CustomerMapper customerMapper;
    @Autowired
    GoodMapper goodMapper;

    @Override
    public WxbCustomer findCustomerByLoginName(String username) {
        Example example = new Example(WxbCustomer.class);
        example.and().andEqualTo("loginName", username);
        return customerMapper.selectOneByExample(example);
    }

    @Override
    public List<WxbGood> findgoodListById(String customerId) {
        Example example = new Example(WxbGood.class);
        example.and().andEqualTo("customerId", customerId);
        List<WxbGood> goodList = goodMapper.selectByExample(example);
        return goodList;
    }

    @Override
    public List<WxbCustomer> findAll() {
        return customerMapper.selectAll();
    }

    @Override
    public WxbCustomer selectById(String customer_id) {
        return customerMapper.selectByPrimaryKey(customer_id);
    }

    @Override
    public WxbCustomer findById(String customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }
}
