package com.example.util;


import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.net.URLEncoder;
import java.util.UUID;

public class QiniuFileManager implements Contants {

    private static QiniuFileManager instance = null;

    private QiniuFileManager() {
    }

    /**
     * 单例模式
     *
     * @return this
     */
    public static QiniuFileManager getInstance() {
        if (instance == null) {
            instance = new QiniuFileManager();
        }
        return instance;
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    public String getUploadToken() {
        Auth auth = Auth.create(QINIU_ACCESSKEY, QINIU_SECRETKEY);
        String upToken = auth.uploadToken(QINIU_BUCKET);
        return upToken;
    }

    /**
     * 上传文件以字节数组形式进行上传
     *
     * @param file 上传的文件，转换为字节数组后的参数
     * @return
     */
    public String uploadFileByQiniu(byte[] file) {
        String rootUrl = "";
        //构造一个带指定Zone对象的配置类：zone2：华南机房
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = UUID.randomUUID().toString();

        try {
            Response response = uploadManager.put(file, key, getUploadToken());
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//            rootUrl = putRet.hash;
            rootUrl = key;

            System.out.println("获取图片上传到七牛后返回的文件名称：" + rootUrl);
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.out.println(r.toString());
        }

        return rootUrl;
    }

    /**
     * 根据上传成功，返回的key值，拼接下载的私有资源完整URL
     *
     * @param qiniuImgName 上传成功返回的hash值
     * @return
     * @throws Exception
     */
    public String getUploadimgOfRootUrl(String qiniuImgName) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(qiniuImgName, "utf-8");
            String publicUrl = String.format("%s/%s", QINIU_ROOT_URL, encodedFileName);
            Auth auth = Auth.create(QINIU_ACCESSKEY, QINIU_SECRETKEY);
//        long expireInSeconds = 3600;//1小时，可以自定义链接过期时间
            String finalUrl = auth.privateDownloadUrl(publicUrl);
//            Logger.getLogger(QiniuFileManager.class).error("获取私有资源的下载URL：" + finalUrl);
            return finalUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

}
