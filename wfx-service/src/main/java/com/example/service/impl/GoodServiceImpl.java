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
        // 代码生成主键（注意：使用UUID，需要将wxb_good表中的主键的 长度 修改为 varchar(60)
        wxbGood.setGoodId(UUID.randomUUID().toString());
        //添加的商品，状态默认为“待审核 0”
        wxbGood.setState(0L);
        return goodMapper.insert(wxbGood)>0;
    }
}
