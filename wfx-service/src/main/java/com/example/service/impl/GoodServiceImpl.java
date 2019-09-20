package com.example.service.impl;

import com.example.mapper.CustomerMapper;
import com.example.mapper.GoodMapper;
import com.example.model.WxbCustomer;
import com.example.model.WxbGood;
import com.example.model.vo.Result;
import com.example.service.GoodService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class GoodServiceImpl implements GoodService {
    @Autowired
    private GoodMapper goodMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public List<WxbGood> selectGoodListBy_cid_tid_stype(String customer_id, String type_id, String sort_type) {
        Example example =new Example(WxbGood.class);
        if (!(customer_id==null||customer_id=="")){
            example.and().andEqualTo("customerId",customer_id);
        }
        if (!(type_id==null||type_id=="")){
            example.and().andEqualTo("typeId",type_id);
        }
        if (!(sort_type==null||sort_type=="")){
                example.orderBy("toped");
        }
        List<WxbGood> wxbGoodList = goodMapper.selectByExample(example);
        Example cexampler=new Example(WxbCustomer.class);
        for (WxbGood good : wxbGoodList) {
            good.setWxbCustomer(customerMapper.selectByPrimaryKey(good.getCustomerId()));
            good.setTheleval(customerMapper.selectByPrimaryKey(good.getCustomerId()).getLevel());
            cexampler.clear();
        }


        return wxbGoodList;
    }

    @Override
    public boolean saveGood(WxbGood wxbGood) {
        // 代码生成主键（注意：使用UUID，需要将wxb_good表中的主键的 长度 修改为 varchar(60)
        wxbGood.setGoodId(UUID.randomUUID().toString());
        //添加的商品，状态默认为“待审核 0”
        wxbGood.setState(0L);
        return goodMapper.insert(wxbGood) > 0;
    }

    @Override
    public List<WxbGood> findGoodByParam(WxbGood param) {
        /*
         * 封装查询条件
         */
        Example example = new Example(WxbGood.class);
        if (param != null) {
            if (StringUtils.isNotBlank(param.getGoodName())) {
                example.and().andLike("goodName", "%" + param.getGoodName() + "%");
            }
            //根据价格区间进行查询
            //由于价格存储的是多个SKU的价格，且价格之间采用"|"分隔，所以查询时，可以先拆分，再查询
//        //由于价格是包含着多个SKU的价格，这时，考虑思路问题
//        if (StringUtils.isNotBlank(param.getSkuPrice())){
//            String[] prices = param.getSkuPrice().split("|");
//
//        }
            //根据state查询数据
            if (param.getState() != null) {
                example.and().andEqualTo("state", param.getState());
            }
        }


        //执行查询
        List<WxbGood> wxbGoods = goodMapper.selectByExample(example);


        return wxbGoods;
    }

    @Override
    public Result updateStateByGoodId(String goodId, String state) {
        if (StringUtils.isNotBlank(goodId) && StringUtils.isNotBlank(state)) {
            //更新商品的状态
            WxbGood wxbGood = new WxbGood();
            wxbGood.setGoodId(goodId);
            wxbGood.setState(Long.parseLong(state));
            //调用更新方法
            boolean result = goodMapper.updateByPrimaryKeySelective(wxbGood) > 0;
            return new Result(result, result ? "操作成功" : "操作失败");
        }
        return new Result(false, "参数缺少,操作失败");
    }

    @Override
    public WxbGood selectBygoodId(String goodId) {
        WxbGood wxbGood = goodMapper.selectByPrimaryKey(goodId);
        wxbGood.setWxbCustomer(customerMapper.selectByPrimaryKey(wxbGood.getCustomerId()));
        return wxbGood;
    }


}
