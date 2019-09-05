package com.example.service.impl;

import com.example.mapper.UserInfoMapper;
import com.example.model.UserInfo;
import com.example.service.UserInfoService;
import org.apache.commons.lang3.StringUtils;
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
    public List<UserInfo> findListByParam(UserInfo userInfo) {
        //根据条件进行查询
        Example example = new Example(UserInfo.class);
        if (userInfo != null){
            //根据用户名和用户帐号 进行模糊查询
            if (StringUtils.isNotBlank(userInfo.getUserName())){
                example.and().andLike("userName","%"+userInfo.getUserName()+"%");
            }
            if (StringUtils.isNotBlank(userInfo.getAccount())){
                example.and().andLike("account","%"+userInfo.getAccount()+"%");
            }
        }
        return userInfoMapper.selectByExample(example);
    }
}
