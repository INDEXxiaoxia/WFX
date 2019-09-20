package com.example.service;

import com.example.Dao.MyWxbGoodRepository;
import com.example.model.WxbGood;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EsServiceTest {
    @Autowired
    MyWxbGoodRepository myWxbGoodRepository;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //
    public List<WxbGood> findGLByCidTidGnameOrderByStp(String customer_id, String type_id, String sort_type, String alisaname) {
        if (customer_id == null) customer_id = "";
        if (type_id == null) customer_id = "";
        if (sort_type == null) sort_type = "";
        if (alisaname == null) alisaname = "";

        WxbGood param = new WxbGood();
        param.setCustomerId(customer_id);
        param.setTypeId(type_id);
        param.setGoodName(alisaname);
//        SearchQuery query = new NativeSearchQuery(QueryBuilders.termQuery("promoteDesc", "华为"));
//        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(query, WxbGood.class);
        //1.构造一个查询的builder对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        if (StringUtils.isNotBlank(param.getCustomerId())) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("customerId", param.getCustomerId()));
        }
        if (StringUtils.isNotBlank(param.getTypeId())) {
            nativeSearchQueryBuilder = nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("typeId", param.getTypeId()));
        }
        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(nativeSearchQueryBuilder.build(), WxbGood.class);
        return wxbGoods;
    }

    public AggregatedPage<WxbGood> testEss2(String customer_id, String type_id, String sort_type, String alisaname,Integer pageP,Integer sizeP) {
        if (customer_id == null) customer_id = "";
        if (type_id == null) type_id = "";
        if (sort_type == null) sort_type = "";
        if (alisaname == null) alisaname = "";
        if (pageP == null) pageP = 0;
        if (sizeP == null) sizeP = 5;

//        SearchQuery query = new NativeSearchQuery(QueryBuilders.termQuery("promoteDesc", "华为"));
//        List<WxbGood> wxbGoods = elasticsearchTemplate.queryForList(query, WxbGood.class);
        //1.构造一个查询的builder对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        //多条件boolean查询
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        //只查询已上架的商品
        builder.must(QueryBuilders.termQuery("state", 1));
        if (StringUtils.isNotBlank(customer_id)) {
//            nativeSearchQueryBuilder.withQuery(QueryBuilders.termQuery("customerId",customer_id));
            builder.must(QueryBuilders.termQuery("customerId", customer_id));
        }
        if (StringUtils.isNotBlank(type_id)) {
            builder.must(QueryBuilders.termQuery("typeId", type_id));
        }
        if (StringUtils.isNotBlank(alisaname)) {
            builder.must(QueryBuilders.fuzzyQuery("goodName", alisaname));
        }
        //将booleanbuilder
        nativeSearchQueryBuilder.withQuery(builder);
        if (StringUtils.isNoneBlank(sort_type)) {
            SortBuilder sortBuilder = null;
            if (sort_type.equals("recomed")) {
                sortBuilder = SortBuilders.fieldSort("recomed").order(SortOrder.DESC);

            } else if (sort_type.equals("level")) {
                sortBuilder = SortBuilders.fieldSort("theleval").order(SortOrder.DESC);

            } else {
                sortBuilder = SortBuilders.fieldSort("toped").order(SortOrder.DESC);

            }
            nativeSearchQueryBuilder.withSort(sortBuilder);//
        }
//        PageRequest pageRequest = new PageRequest(0,1000);
        Pageable pageable = PageRequest.of(pageP,sizeP);
//        List<WxbGood> wxbGoods =
        AggregatedPage<WxbGood> pagewxbGoods = elasticsearchTemplate.queryForPage(nativeSearchQueryBuilder.withPageable(pageable).build(), WxbGood.class);
        return pagewxbGoods;


    }


}
