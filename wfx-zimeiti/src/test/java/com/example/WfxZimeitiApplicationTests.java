package com.example;

import com.example.Dao.MyUseTool;
import com.example.Dao.MyWxbGoodRepository;
import com.example.mapper.GoodMapper;
import com.example.model.WxbGood;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WfxZimeitiApplicationTests {
    @Autowired
    MyWxbGoodRepository myWxbGoodRepository;
@Autowired
    GoodMapper goodMapper;
    @Test
    public void contextLoads() {
    }

    @Test
    public void SPPPP() {
//        splitTitle[i], splitPrice[i], splitPmoney[i])
        WxbGood wxbGood = new WxbGood();
        wxbGood.setSkuTitle("A|B|C");
        wxbGood.setSkuPrice("10|20|30");
        wxbGood.setSkuPmoney("2|4|6");
        System.out.println(wxbGood);
        System.out.println(wxbGood.getSkuStringList());
    }

    @Test
    public void SPPPPP() {
        String[] split = "AB|CD|E".split("|");
        for (String s : split) {
            System.out.println(s);
        }
    }

    @Test
    public void CLLLONE() throws CloneNotSupportedException {
        WxbGood wxbGood = new WxbGood();
        wxbGood.setSkuTitle("A|B|C");
        wxbGood.setSkuPrice("10|20|30");

        WxbGood wxbGood1 = (WxbGood) wxbGood.clone();
        wxbGood.setSkuPmoney("2|4|6");
        System.out.println(wxbGood);
        System.out.println(wxbGood1);
    }
    @Test
        //向es中插入原始数据
    public void importDataToEs(){
        List<WxbGood> wxbGoods = goodMapper.selectAll();
        for (WxbGood wxbGood : wxbGoods) {
            myWxbGoodRepository.save(wxbGood);
        }
    }
    @Test
    public void EsTest01(){
        List<WxbGood> qf283942 = myWxbGoodRepository.findByCustomerIdAndTypeIdAndGoodNameLikeOrderByToped("qf283942", "01", "");
        System.out.println(qf283942);
    }

}
