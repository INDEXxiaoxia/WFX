package com.example.service;

import com.example.model.UserInfo;

public interface UserInfoService {

    UserInfo findUserInfoByUserName(String userName);
}
