package com.example.demo;

import java.io.IOException;


import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class QiniuTest {
    private static int getRandom(int count) {
        return (int) Math.round(Math.random() * (count));
    }

    private static String string = "abcdefghijklmnopqrstuvwxyz";

    private static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        int len = string.length();
        for (int i = 0; i < length; i++) {
            sb.append(string.charAt(getRandom(len - 1)));
        }
        return sb.toString();
    }

    // 设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY = "K8WWlWVTd6-p4WzoIh0IygahkTXgy2dztQVD1Tpk"; // 这两个登录七牛
            // 账号里面可以找到  进入 个人面板->个人中心->密钥管理可找到
    String SECRET_KEY = "yUG0Z81iv0ZpdW3vXGtGwUOKJVOoJNNYin4lMqaQ";//

    // 要上传的空间
    String bucketname = "schoolwall"; // 填写新建的那个存储空间对象的名称
    // 上传到七牛后保存的文件名
    String key = getRandomString(10) + ".jpg";//七牛云服务器里用来对应唯一上传的文件
    // String audioUrl = "在七牛云中找到";
    // String imagesUrl = "在七牛云中找到";
    // 上传文件的路径
    String FilePath = "D:\\demo\\海报.png"; // 本地要上传文件路径

    // 密钥配置
    Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    
    Configuration cfg = new Configuration(Zone.zone0());
    // 创建上传对象
    UploadManager uploadManager = new UploadManager(cfg);

    // 简单上传，使用默认策略，只需要设置上传的空间名就可以了 //
    public String getUpToken() {
        return auth.uploadToken(bucketname);
    }

    // 普通上传
    public void upload() throws IOException {
        try {
            // 调用put方法上传
            Response res = uploadManager.put(FilePath, key, getUpToken());
            // 打印返回的信息

            System.out.println(res.isOK());

            System.out.println(res.bodyString());
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                // ignore
            }
        }
    }

    public static void main(String args[]) throws IOException {
        new QiniuTest().upload();
    }

}

