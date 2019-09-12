package com.example;

import com.example.dao.WxbGoodRepository;
import com.example.model.WxbGood;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ElasticsearchQueryParamTest {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private WxbGoodRepository wxbGoodRepository;

    @Test
    public void testTermQuery() {


        SearchQuery query = new NativeSearchQuery(QueryBuilders.termQuery("promoteDesc", "华为"));
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(query, WxbGood.class);
//        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
//        nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("goodName", "华泰"));
//        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }


    @Test
    public void testFindByParam() {
        WxbGood param = new WxbGood();
        param.setGoodName("千锋");
        param.setTypeId("01");

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        if (StringUtils.isNotBlank(param.getGoodName())) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.wildcardQuery("goodName", "*" + param.getGoodName() + "*"));
        }

        if (StringUtils.isNotBlank(param.getTypeId())) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("typeId", param.getTypeId()));
        }

        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }

    }

    @Test
    public void testFindByKeywords() {
        String keyword = "华为";
        BoolQueryBuilder should = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("goodName", "*" + keyword + "*")).should(QueryBuilders.termQuery("promoteDesc", keyword));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(should).build();
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(searchQuery, WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }


}
