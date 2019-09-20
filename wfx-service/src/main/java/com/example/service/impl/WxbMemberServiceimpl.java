package com.example.service.impl;

import com.example.mapper.WxbMemberMapper;
import com.example.model.WxbMemeber;
import com.example.service.WxbMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

@Service
@Transactional
public class WxbMemberServiceimpl implements WxbMemberService {
    @Autowired
    private WxbMemberMapper wxbMemberMapper;

    @Override
    public WxbMemeber findMemberByUsername(String username) {

        Example example=new Example(WxbMemeber.class);
        example.and().andEqualTo("account",username);
        return wxbMemberMapper.selectOneByExample(example);
    }
}
