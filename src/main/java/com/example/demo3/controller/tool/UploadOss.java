package com.example.demo3.controller.tool;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class UploadOss//ok
{
    //阿里域名
    public static final String ALI_DOMAIN="https://zqy-287189.oss-cn-beijing.aliyuncs.com/";

    public static final String BUCKET_NAME="zqy-287189";

    public static String uploadImage(MultipartFile file, String username) throws IOException {

        //获取文件名
        String originalFilename = file.getOriginalFilename();
        //获取文件后缀
        String suffix = "";
        if(originalFilename!=null)
        {
            suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        //生成时间戳作为文件名
        String filename = System.currentTimeMillis() + suffix;
        //构建目标路径
        String path = "DeliverySystem/" + username + "/" + filename;

//        //生成六个随机数防止重复
//        Random random = new Random();
//        String randomNumber1 = String.valueOf(random.nextInt(1000001)); // 生成 0 到 10000 之间的随机整数
//        String randomNumber2 = String.valueOf(random.nextInt(1000001)); // 生成 0 到 10000 之间的随机整数
//        String randomNumber3 = String.valueOf(random.nextInt(1000001)); // 生成 0 到 10000 之间的随机整数
//        String randomNumber4 = String.valueOf(random.nextInt(1000001)); // 生成 0 到 10000 之间的随机整数
//        String randomNumber5 = String.valueOf(random.nextInt(1000001)); // 生成 0 到 10000 之间的随机整数
//        String randomNumber6 = String.valueOf(random.nextInt(1000001)); // 生成 0 到 10000 之间的随机整数
//        String filename=randomNumber1+"x"+randomNumber2+"y"+randomNumber3+"z"+randomNumber4+"i"+randomNumber5+"j"+randomNumber6;//将六个随机数组合作为文件名
//        //url编码
//        filename = URLEncoder.encode(filename, "UTF-8");


        //地域结点
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        String accessKeyId = "LTAI5tSC2jqvERCtYKRvMeSX";
        String accessKeySecret = "kWQnvih6FSATmiIKqvIdoB3gMFHcVQ";

//oss客户存储对象
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 创建PutObjectRequest对象
        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET_NAME, path, file.getInputStream());
// 上传文件到阿里云 OSS
        ossClient.putObject(putObjectRequest);
        System.out.println("返回的路径（数据库存的路径）："+ALI_DOMAIN+path);
// 关闭客户端
        ossClient.shutdown();
        return ALI_DOMAIN+path;
    }
}
