package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.model.vo.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.UUID;

@Controller
@RequestMapping("/demo")
public class DemoController {

    @RequestMapping("/init-kindeditor")
    public String init() {
        return "kindeditor-demo";
    }

    @RequestMapping("/fileUplad")
    @ResponseBody
    public HashMap fileUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile imgFile) {
        /*
            1.在java中可以获取工程的两种路径：
                服务器路径：当前服务器所部署的路径：request.getServletContext().getRealPath("/remote_image")
                本地程序的路径 ：通过 File对象操作 new File(parentPath,fileName)

         */
        //改造的目的：是希望将界面中传递过来的图片，直接保存到 /resources/static目录下。如果保存在静态Static目录，直接通过/文件名 访问
        //下面的这个方法，可以获取springboot工程中，指定静态资源的绝对路径目录
        String path = ClassUtils.getDefaultClassLoader().getResource("static").getPath();
        //实现将imageFile保存到springboot静态资源目录下
        try {
            //1 获取的上传的文件的完整名称（包含后缀）
            String fileName = imgFile.getOriginalFilename();
            //2 生成一个文件的随机名称，防止重复
            String urlFileName = UUID.randomUUID().toString() + fileName.substring(fileName.lastIndexOf("."));
            File file = new File(path, urlFileName);//创建一个对象
            //4 编写自定义方法，将imageFile对象参数保存到指定目录中
            writeToLocal(file, imgFile.getInputStream());
            //5 将上传成功后的图片，返回给界面，进行显示
            HashMap map = new HashMap();
            map.put("error", 0);
            map.put("url", "/" + urlFileName);
            return map;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }


    }

    private static void writeToLocal(File destination, InputStream input)
            throws IOException {
        int index;
        byte[] bytes = new byte[1024];
        FileOutputStream downloadFile = new FileOutputStream(destination);
        while ((index = input.read(bytes)) != -1) {
            downloadFile.write(bytes, 0, index);
            downloadFile.flush();
        }
        downloadFile.close();
        input.close();
    }
}
