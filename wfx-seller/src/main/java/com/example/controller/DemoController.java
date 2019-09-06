package com.example.controller;

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
    public String fileuUpload(Model model, MultipartFile file) {

        if (file != null) {
            try {
                String url = QiniuFileManager.getInstance().uploadFileByQiniu(file.getBytes());
                model.addAttribute("imgUrl", QiniuFileManager.getInstance().getUploadimgOfRootUrl(url));
                return "demo/file-upload";
            } catch (IOException e) {
                e.printStackTrace();
                return "index";
            }
        }
        return "index";
    }
}