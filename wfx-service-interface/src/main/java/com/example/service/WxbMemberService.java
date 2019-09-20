package com.example.service;

import com.example.model.WxbCustomer;
import com.example.model.WxbMemeber;

public interface WxbMemberService {
    WxbMemeber findMemberByUsername(String username);
}
