package com.example.controller;

import com.example.model.WxbGood;
import com.example.util.QiniuFileManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/init-upload")
    public String initUpload() {
        return "demo/file-upload";
    }

    /**
     * 文件上传测试
     *
     * @return
     */
    @RequestMapping("/fileUpload")
    public String fileuUpload(Model model, WxbGood good, MultipartFile file, MultipartFile file1, MultipartFile file2) {// MultipartFile的变量名必须和表单提交file的name名称相同
        //思路分析：
        /*
            在对商品的图片信息进行保存操作时，分分成两步来实现：
                1）先将提交的图片，保存到图片服务器（七牛）
                2）保存成功后，还需要将生成的外网可以访问的图片地址进行返回（保存到数据库）
                3）再将要保存的 表单信息 WxbGood对象的数据，保存到数据库
                            数据表： wxb_good

         */
        if (file != null){
            String goodPic = uploadToQiniuAndReturnInternetUrl(file);
            good.setGoodPic(goodPic);
        }
        if (file1 != null){
            String goodPic1 = uploadToQiniuAndReturnInternetUrl(file1);
            good.setGoodPic1(goodPic1);
        }
        if (file2 != null){
            String goodPic2 = uploadToQiniuAndReturnInternetUrl(file2);
            good.setGoodPic2(goodPic2);
        }
        //最后，调用 WxbGood的service方法，将数据保存到数据库
        System.out.println(good);
        return "index";
    }

    /**
     * 将一个MultipartFile类型的文件，上传到七牛服务器，然后返回访问这个图片的外网链接地址
     * @param file
     * @return
     */
    private String uploadToQiniuAndReturnInternetUrl(MultipartFile file) {
        //调用 七牛的工具类，将当前这个图片以 字节流的方式，上传到七牛的服务器。上传成功后，会将成功上传的图片的名称返回回来
        String url = null;
        try {
            String fileQiniuName = QiniuFileManager.getInstance().uploadFileByQiniu(file.getBytes());
            url = QiniuFileManager.getInstance().getUploadimgOfRootUrl(fileQiniuName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }
}