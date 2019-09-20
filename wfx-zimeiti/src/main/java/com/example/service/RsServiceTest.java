package com.example.service;

import com.example.model.WxbCustomer;
import com.example.model.WxbGood;
import com.example.model.WxbGoodType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RsServiceTest {
    @Autowired
    private RedisTemplate redisTemplate;

    private static final String SPARAM = "SPARAM";

    public boolean isSamee (String customer_id, String type_id, String sort_type,String alisaname,Integer pageP,Integer sizeP){
        String customer_idR = (String) redisTemplate.boundHashOps(SPARAM).get("customer_id");
        String type_idR = (String) redisTemplate.boundHashOps(SPARAM).get("type_id");
        String sort_typeR = (String) redisTemplate.boundHashOps(SPARAM).get("sort_type");
        String alisanameR = (String) redisTemplate.boundHashOps(SPARAM).get("alisaname");
        Integer pagePR = (Integer) redisTemplate.boundHashOps(SPARAM).get("pageP");
        if (customer_id.equals(customer_idR)&&type_id.equals(type_idR)&&sort_type.equals(sort_typeR)&&alisaname.equals(alisanameR)&&pageP.equals(pagePR)){

            System.out.println("查询结果与Redis中相同，从redis中读取数据！");
            return true;//相同不用存 并且直接从redis中取
//            return false;//有bug  不能读取对象的子对象
        }else {
            //存储
            System.out.println("查询条件不同！正在向redis中存储");
            redisTemplate.boundHashOps(SPARAM).put("customer_id",customer_id);
            redisTemplate.boundHashOps(SPARAM).put("type_id",type_id);
            redisTemplate.boundHashOps(SPARAM).put("sort_type",sort_type);
            redisTemplate.boundHashOps(SPARAM).put("alisaname",alisaname);
            redisTemplate.boundHashOps(SPARAM).put("pageP",pageP);
            return false;
        }
    }

    public void savePageList(AggregatedPage<WxbGood> wxbGoodList){
        redisTemplate.boundHashOps(SPARAM).put("wxbGoodList",wxbGoodList);
        System.out.println("结果已存储到redis");
    }
    public  AggregatedPage<WxbGood> getPagelist(){
        AggregatedPage<WxbGood> wxbGoodList = (AggregatedPage<WxbGood>) redisTemplate.boundHashOps(SPARAM).get("wxbGoodList");
        return wxbGoodList;
    }

    public boolean SaveDownPullList(List<WxbCustomer> wxbCustomerList ,List<WxbGoodType> wxbGoodTypeList){
        //存储下拉列表
        redisTemplate.boundHashOps(SPARAM).put("wxbCustomerList",wxbCustomerList);
        redisTemplate.boundHashOps(SPARAM).put("wxbGoodTypeList",wxbGoodTypeList);
        return true;
    }
    //取下拉列表
    public List<WxbCustomer> getwxbCustomerListR(){
        System.out.println("正在从redis中获取商户列表");
        List<WxbCustomer> wxbCustomerList= (List<WxbCustomer>) redisTemplate.boundHashOps(SPARAM).get("wxbCustomerList");
        return wxbCustomerList;
    }
    public List<WxbGoodType> getwxbGoodTypeListR(){
        System.out.println("正在从redis中获取商品类型列表");
        List<WxbGoodType> wxbGoodTypeList= (List<WxbGoodType>) redisTemplate.boundHashOps(SPARAM).get("wxbGoodTypeList");
        return wxbGoodTypeList;
    }
}
