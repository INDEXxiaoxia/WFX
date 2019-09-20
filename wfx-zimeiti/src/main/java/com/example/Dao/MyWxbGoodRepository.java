package com.example.Dao;

import com.example.model.WxbGood;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface MyWxbGoodRepository extends ElasticsearchRepository<WxbGood,String> {

    //根据指定的属性名进行查询
    List<WxbGood> findByTypeId(String typeId);
    //根据多个条件进行查询
    //如果查询的方法中包含多个属性，那么后面参数的个数一定要与方法中包含的属性个数一致
    List<WxbGood> findByTypeIdAndState(String typeId,String state);
    List<WxbGood> findByTypeIdOrState(String typeId,String state);
    //根据一个关键词，同时查询goodName和promote_desc字段中都包含关键字的数据
    List<WxbGood> findByGoodNameLikeOrPromoteDescLike(String goodName,String promoteDesc);
    //模糊查询
    List<WxbGood> findByGoodNameLike(String goodName);
    Page<WxbGood> findByGoodNameLike(String goodName, Pageable pageable);

    List<WxbGood> findByCustomerIdAndTypeIdAndGoodNameLikeOrderByToped(String customer_id,String type_id,String alisaname);
    List<WxbGood> findByCustomerIdAndTypeIdAndGoodNameLikeOrderByRecomed(String customer_id,String type_id,String alisaname);
    List<WxbGood> findByCustomerIdAndTypeIdAndGoodNameLike(String customer_id,String type_id,String alisaname);
    List<WxbGood> findAllBy();
    List<WxbGood> findByCustomerIdAndTypeIdAndGoodNameLikeOrderByTheleval(String customer_id,String type_id,String alisaname);

}
