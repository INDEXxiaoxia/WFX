package com.example.demo;

import com.alibaba.fastjson.JSONObject;
import com.example.model.vo.Result;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
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
    public HashMap<String, String> fileUpload(HttpServletRequest request, HttpServletResponse response, MultipartFile imgFile) {
        /*
            1.在java中可以获取工程的两种路径：
                服务器路径：当前服务器所部署的路径：request.getServletContext().getRealPath("/remote_image")
                本地程序的路径 ：通过 File对象操作 new File(parentPath,fileName)

         */

//        String path = request.getServletContext().getRealPath("/remote_image");

        //保存返回的结果
        JSONObject obj = new JSONObject();

        //实现将imageFile保存到本地路径
        try {
            //1 获取的上传的文件的完整名称（包含后缀）
            String fileName = imgFile.getOriginalFilename();
            //2 创建将文件保存到本地磁盘的文件目录
            String parentPath = "d:\\wfx-images";
            //3 优化：在保存图片到本地指定目录时，需要确保图片的名称唯一
            String urlFileName = UUID.randomUUID().toString() + "." + fileName.substring(fileName.lastIndexOf("."));
            File file = new File(parentPath, urlFileName);//创建一个对象
            //4 编写自定义方法，将imageFile对象参数保存到指定目录中
            writeToLocal(file, imgFile.getInputStream());
            //5 将上传成功后的图片，返回给界面，进行显示

//            //下面的代码，即为kindeditor官方案例中提供的返回文件上传成功后的 返回结果集的拼接
//            PrintWriter out = new PrintWriter(response.getOutputStream());
//            obj.put("error", 0);
//            obj.put("url", "http://localhost:8080/001.jpg");
//            out.println(obj.toJSONString());
            HashMap map = new HashMap();
            map.put("error", 0);
            map.put("url", "http://localhost:8080/001.jpg");
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
