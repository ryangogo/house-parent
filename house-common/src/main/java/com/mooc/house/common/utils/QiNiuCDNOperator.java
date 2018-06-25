package com.mooc.house.common.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.persistent.FileRecorder;
import com.qiniu.util.Auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Paths;

/**
 * Created by Administrator on 2018/5/18.
 */
public class QiNiuCDNOperator {

    private static String accessKey = "s53_G0HRGy857JX7tr80UZbQiBOJs8LyNJvrkaez";
    private static String secretKey = "bdfkfluk3BHZItshBzOhgmSoiYRtCnfX2MHomQAF";
    private static String bucket = "ryan-test";


    private static Logger logger = LoggerFactory.getLogger(QiNiuCDNOperator.class);

    /**
     * 华东 Zone.zone0()
     * 华北 Zone.zone1()
     * 华南 Zone.zone2()
     * 北美 Zone.zoneNa0()
     *
     * @param args
     */
    public static void main(String[] args) {
        /*上传到七牛云并且返回DefaultPutRet*/
        /*DefaultPutRet dp = fileUpload(Zone.zone0(),QiNiuCDNOperator.getUploadCredential(),"D:\\ftpfile\\img\\8a8d63ab-92f2-4845-8209-992104950a78.jpg");*/
        DefaultPutRet dp = fileUpload(Zone.zone0(), QiNiuCDNOperator.getUploadCredential(), "D:\\ftpfile\\img\\e7a1abef-c829-4068-afbc-26621ef6778d.jpg");
        /*公有空间返回文件URL*/
        System.out.println(dp.key);
    }


    /**
     * 获取上传凭证
     */
    public static String getUploadCredential() {
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        System.out.println(upToken);
        return upToken;
    }

    /**
     * 文件上传
     *
     * @param file
     * @param key
     * @return
     */
    public static DefaultPutRet fileUpload(FileInputStream file, String key) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone0());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //String bucket = "oy09glbzm.bkt.clouddn.com";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(file, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
//                System.out.println(putRet.key);
//                System.out.println(putRet.hash);
                return putRet;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 文件上传
     *
     * @param zone          华东    Zone.zone0()
     *                      华北    Zone.zone1()
     *                      华南    Zone.zone2()
     *                      北美    Zone.zoneNa0()
     * @param upToken       上传凭证
     * @param localFilePath 需要上传的文件本地路径
     * @return
     */
    public static DefaultPutRet fileUpload(Zone zone, String upToken, String localFilePath) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            Response response = uploadManager.put(localFilePath, key, upToken);
            // 解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            System.out.println(putRet.key);
            System.out.println(putRet.hash);
            return putRet;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                // ignore
            }
        }
        return null;
    }

    /**
     * 公有空间返回文件URL
     *
     * @param fileName
     * @return
     */
    public static String publicFile(String fileName) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String finalUrl = String.format("%s/%s", bucket, encodedFileName);
        System.out.println(finalUrl);
        return finalUrl;
    }

    /**
     * 私有空间返回文件URL
     *
     * @param auth
     * @param fileName
     * @param domainOfBucket
     * @param expireInSeconds
     * @return
     */
    public static String privateFile(Auth auth, String fileName, String domainOfBucket, long expireInSeconds) {
        String encodedFileName = null;
        try {
            encodedFileName = URLEncoder.encode(fileName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String publicUrl = String.format("%s/%s", domainOfBucket, encodedFileName);
        String finalUrl = auth.privateDownloadUrl(publicUrl, expireInSeconds);
        System.out.println(finalUrl);
        return finalUrl;
    }

    /**
     * 字符组上传
     *
     * @param zone
     * @param upToken
     * @return
     */
    public static DefaultPutRet charArrayUpload(Zone zone, String upToken) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");

            try {
                Response response = uploadManager.put(uploadBytes, key, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return putRet;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    // ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            // ignore
        }
        return null;
    }

    /**
     * 数据流上传
     *
     * @param zone
     * @param upToken
     * @return
     */
    public static DefaultPutRet streamUpload(Zone zone, String upToken) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        // ...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        // ...生成上传凭证，然后准备上传
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            byte[] uploadBytes = "test streamUpload \n hello qiniu cloud".getBytes("utf-8");
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            try {
                Response response = uploadManager.put(byteInputStream, key, upToken, null, null);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return putRet;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    // ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            // ignore
        }
        return null;
    }

    /**
     * 断点续传
     *
     * @param zone
     * @param upToken
     * @param localFilePath
     * @return
     */
    public static DefaultPutRet breakPointUpload(Zone zone, String upToken, String localFilePath) {
        // 构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(zone);
        // ...其他参数参考类注释
        // 如果是Windows情况下，格式是 D:\\qiniu\\test.png
        // 默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        String localTempDir = Paths.get(System.getenv("java.io.tmpdir"), bucket).toString();
        try {
            // 设置断点续传文件进度保存目录
            FileRecorder fileRecorder = new FileRecorder(localTempDir);
            UploadManager uploadManager = new UploadManager(cfg, fileRecorder);
            try {
                Response response = uploadManager.put(localFilePath, key, upToken);
                // 解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return putRet;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    // ignore
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // MyPutRet myPutRet=response.jsonToObject(MyPutRet.class);
        return null;
    }


}
