package com.example.service;

import com.example.model.WxbCustomer;
import com.example.model.WxbGood;

import java.util.List;

public interface CustomerService {
    WxbCustomer findCustomerByLoginName(String username);

    List<WxbGood> findgoodListById(String customerId);

    List<WxbCustomer> findAll();

    WxbCustomer selectById(String customer_id);

    WxbCustomer findById(String customerId);
}
