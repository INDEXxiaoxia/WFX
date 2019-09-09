package com.example.service.impl;

import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import com.example.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;

    @Override
    public boolean saveGood(WxbGood wxbGood) {
        wxbGood.setGoodId(UUID.randomUUID().toString());
        //添加的商品，状态默认为“待审核 0”
        wxbGood.setState(0L);
        return goodMapper.insert(wxbGood)>0;
    }
}
