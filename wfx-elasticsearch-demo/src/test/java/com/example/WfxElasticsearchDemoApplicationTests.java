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
import java.util.UUID;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WfxElasticsearchDemoApplicationTests {

    @Autowired
    private GoodMapper goodMapper;

    @Autowired
    private WxbGoodRepository wxbGoodRepository;
    @Test
    public void contextLoads() {
        WxbGood wxbGood = goodMapper.selectByPrimaryKey("28770956-4811-442c-a89c-c2c30fafe6de");
        wxbGood.setGoodId(UUID.randomUUID().toString());
        wxbGood.setGoodName("2019年9月11日的测试数据");

        //调用elastisearchRepository接口，将这个数据添加 ES的服务器中
        wxbGoodRepository.save(wxbGood);
//        List<WxbGood> wxbGoods = goodMapper.selectAll();
//        for (WxbGood wxbGood : wxbGoods) {
//            System.out.println(wxbGood);
//            wxbGoodRepository.save(wxbGood);
//        }
    }

    @Test
    public void testFindAll(){
        Iterable<WxbGood> all = wxbGoodRepository.findAll();
        for (WxbGood wxbGood : all) {
            System.out.println(wxbGood);
        }
    }

    @Test
    public void testHighlistQuery(){

    }

}
