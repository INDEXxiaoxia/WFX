package com.example.service;

import com.example.model.UserInfo;

import java.util.List;

public interface UserInfoService {

    UserInfo findUserInfoByUserName(String userName);

    List<UserInfo> findListByParam();
}
