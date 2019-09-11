package com.example.dao;

import com.example.model.WxbGood;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface WxbGoodRepository extends ElasticsearchRepository<WxbGood,String> {
}
