package com.example;

import com.example.dao.WxbGoodRepository;
import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WfxElasticsearchDemoApplicationTests {

    @Autowired
    private GoodMapper goodMapper;


    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private WxbGoodRepository wxbGoodRepository;


    @Test
    public void importDataToEs(){
        List<WxbGood> wxbGoods = goodMapper.selectAll();
        for (WxbGood wxbGood : wxbGoods) {
            wxbGoodRepository.save(wxbGood);
        }
    }

    @Test
    public void contextLoads() {
        WxbGood wxbGood = goodMapper.selectByPrimaryKey("28770956-4811-442c-a89c-c2c30fafe6de");
        wxbGood.setGoodId("38fd647c-fd55-47e4-8d13-810fb5b4ddfa");
        wxbGood.setGoodName("2019年13月11日的测试数据");
        //调用elastisearchRepository接口，将这个数据添加 ES的服务器中
        wxbGoodRepository.save(wxbGood);

    }

    @Test
    public void testDelete(){
        wxbGoodRepository.deleteById("38fd647c-fd55-47e4-8d13-810fb5b4ddfa");
    }

    @Test
    public void testFindById(){
        Optional<WxbGood> optionalWxbGood = wxbGoodRepository.findById("06ab3de6-d35c-4c2b-8b0d-3a69700ccdf0");
        WxbGood wxbGood = optionalWxbGood.get();
        System.out.println(wxbGood);
    }

    @Test//根据指定条件查询
    public void testFindByTypeId(){
        List<WxbGood> byTypeId = wxbGoodRepository.findByTypeId("01");
        for (WxbGood wxbGood : byTypeId) {
            System.out.println(wxbGood);
        }
    }

    @Test//根据多个条件进行查询
    public void testFindByTypeIdAndState(){
        List<WxbGood> wxbGoodList = wxbGoodRepository.findByTypeIdAndState("01","1");
        for (WxbGood wxbGood : wxbGoodList) {
            System.out.println(wxbGood);
        }
    }

    @Test//模糊查询
    public void testFindByGoodNameLike(){
        List<WxbGood> wxbGoods = wxbGoodRepository.findByGoodNameLike("%千锋%");
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }

    @Test
    public void testFindAll(){
        Iterable<WxbGood> all = wxbGoodRepository.findAll();
        for (WxbGood wxbGood : all) {
            System.out.println(wxbGood);
        }
    }

    @Test//不带条件的分页查询
    public void testFindAllPageQuery(){
        Pageable pageable = PageRequest.of(0,4);
        Page<WxbGood> page = wxbGoodRepository.findAll(pageable);
        System.out.println("符合条件的总数："+page.getTotalElements());
        System.out.println("总共查询出页数："+page.getTotalPages());
        //当前页的数据
        for (WxbGood wxbGood : page.getContent()) {
            System.out.println(wxbGood);
        }
    }

    @Test
    public void testFindByGoodNameListPageQuery(){
        Pageable pageable = PageRequest.of(0,4);
        Page<WxbGood> page = wxbGoodRepository.findByGoodNameLike("%千锋%", pageable);
        System.out.println("符合条件的总数："+page.getTotalElements());
        System.out.println("总共查询出页数："+page.getTotalPages());
        //当前页的数据
        for (WxbGood wxbGood : page.getContent()) {
            System.out.println(wxbGood);
        }
    }

    @Test
    public void testFindByKeyword(){
        String keyword = "华为";

        List<WxbGood> list = wxbGoodRepository.findByGoodNameLikeOrPromoteDescLike("%" + keyword + "%", "%" + keyword + "%");
        for (WxbGood wxbGood : list) {
            System.out.println(wxbGood);
        }

    }

}
