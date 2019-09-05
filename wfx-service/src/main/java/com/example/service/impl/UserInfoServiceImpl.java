package com.example.service.impl;

import com.example.mapper.UserInfoMapper;
import com.example.model.UserInfo;
import com.example.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public UserInfo findUserInfoByUserName(String userName) {
        Example example = new Example(UserInfo.class);
        example.and().andEqualTo("account",userName);
        return userInfoMapper.selectOneByExample(example);
    }

    @Override
    public List<UserInfo> findListByParam() {

        return userInfoMapper.selectAll();
    }
}
