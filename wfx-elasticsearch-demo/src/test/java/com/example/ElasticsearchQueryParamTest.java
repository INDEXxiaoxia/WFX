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
        WxbGood param = new WxbGood();
        param.setPromoteDesc("华为");
//        SearchQuery query = new NativeSearchQuery(QueryBuilders.termQuery("promoteDesc", "华为"));
//        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(query, WxbGood.class);
        //1.构造一个查询的builder对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (StringUtils.isNotBlank(param.getGoodName())){
            nativeSearchQueryBuilder =nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("goodName", param.getGoodName()));
        }
        if (StringUtils.isNotBlank(param.getPromoteDesc())){
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("promoteDesc",param.getPromoteDesc()));
        }
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }


    @Test// wildcardQuery的用法
    public void testFindByParam() {
        WxbGood param = new WxbGood();
//        param.setGoodName("千锋");
//        param.setTypeId("01");
        param.setPromoteDesc("光徕卡");

        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();

        if (StringUtils.isNotBlank(param.getPromoteDesc())) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.wildcardQuery("promoteDesc", "*" + param.getPromoteDesc() + "*"));
        }


        if (StringUtils.isNotBlank(param.getTypeId())) {
            nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("typeId", param.getTypeId()));
        }

        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }

    }

    /*
        相似度匹配查询：
            规则：匹配的条件与 要查询字段的分词后的所有词条进行比较
                如果和分词出来的某一个词条，相似度超过了50%，则认为是符合要求的数据
     */
    @Test
    public void testFuzzyQUery(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(QueryBuilders.fuzzyQuery("promoteDesc","操速音速卡"));
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }

    @Test
    public void testMartchQuery(){
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        /*
            martchQuery查询的原理： 先将参数“操速音速卡”进行分词，分词之后的每一个词条，再与es服务器中的每条的词条进行匹配。
            martchQuery默认的分词方式：一个字一个词
         */
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("promoteDesc","操速音速卡"));
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }


    @Test
    public void testFindByKeywords() {
        String keyword = "华为";
        /*
            BoolQueryBuilder 用来拼接多个查询条件
                eg:两个条件之间，希望使用 or 来连接
            在es的查询语法中
             and ----- must
             or ------ should

         */
        BoolQueryBuilder should = QueryBuilders.boolQuery().should(QueryBuilders.wildcardQuery("goodName", "*" + keyword + "*")).should(QueryBuilders.termQuery("promoteDesc", keyword));
        NativeSearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(should).build();
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(searchQuery, WxbGood.class);
        for (WxbGood wxbGood : wxbGoods) {
            System.out.println(wxbGood);
        }
    }


}
