package com.example;

import com.example.dao.WxbGoodRepository;
import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WfxElasticsearchDemoApplicationTests {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private WxbGoodRepository wxbGoodRepository;
    @Test
    public void contextLoads() {
        List<WxbGood> wxbGoods = goodMapper.selectAll();
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
            wxbGoodRepository.save(wxbGood);
        }
    }

    @Test
    public void testFindAll(){
        Iterable<WxbGood> all = wxbGoodRepository.findAll();
        for (WxbGood wxbGood : all) {
            System.out.println(wxbGood);
        }
    }

}
