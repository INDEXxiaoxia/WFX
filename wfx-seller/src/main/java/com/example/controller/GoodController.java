package com.example.controller;

import com.example.model.WxbCustomer;
import com.example.model.WxbGood;
import com.example.service.CustomerService;
import com.example.service.GoodService;
import com.example.util.QiniuFileManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/good")
public class GoodController {

    @Autowired
    private GoodService goodService;
    @Autowired
    private CustomerService customerService;
    @RequestMapping("/saveGood")
    public String saveGood(Model model, WxbGood good, MultipartFile pic1, MultipartFile pic2, MultipartFile pic3) {

        WxbCustomer ThisCustomer= (WxbCustomer) SecurityUtils.getSubject().getPrincipal();
        //执行操作前，先将图片进行上传
        //调用七牛图片上传接口
        try {
            String picName1 = QiniuFileManager.getInstance().uploadFileByQiniu(pic1.getBytes());
            String picName2 = QiniuFileManager.getInstance().uploadFileByQiniu(pic2.getBytes());
            String picName3 = QiniuFileManager.getInstance().uploadFileByQiniu(pic3.getBytes());
            //将图片的完整访问地址保存到good实体类中
            if (StringUtils.isNotBlank(picName1)) {
                good.setGoodPic(QiniuFileManager.getInstance().getUploadimgOfRootUrl(picName1));
            }
            if (StringUtils.isNotBlank(picName1)) {
                good.setGoodPic1(QiniuFileManager.getInstance().getUploadimgOfRootUrl(picName2));
            }
            if (StringUtils.isNotBlank(picName1)) {
                good.setGoodPic2(QiniuFileManager.getInstance().getUploadimgOfRootUrl(picName3));
            }
            good.setCustomerId(ThisCustomer.getCustomerId());
            good.setCreateTime(new Date());
            good.setRecomed(0L);
            good.setToped(0L);
        } catch (IOException e) {
            e.printStackTrace();
        }

        boolean result = goodService.saveGood(good);
        if (result) {
//            return "WxbGoodVO-list";
            return  "redirect:/good/CustomerSeeGoods";
        } else {
            return "goods-add";
        }
    }

    @RequestMapping("/CustomerSeeGoods")
    public String ShowGoodsList(Model model){
        WxbCustomer wxbCustomer= (WxbCustomer) SecurityUtils.getSubject().getPrincipal();
        List<WxbGood> goodList=customerService.findgoodListById(wxbCustomer.getCustomerId());
        model.addAttribute("LoginCustomer",wxbCustomer);
        model.addAttribute("goodList",goodList);
        return "商品信息管理";

    }
    @RequestMapping("/addGood")
    public String addGoodViews(){
        return "goods-add";
    }
}
