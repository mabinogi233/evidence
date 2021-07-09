package com.project.evidence.logModule;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;

@Service("ossService")
public class OSSService {

    //OSS服务器参数
    private static final String endpoint ="oss-cn-beijing.aliyuncs.com";
    private static final String accessKeyId= "";
    private static final String secretAccessKey= "";
    private static final String bucketName="lwz-evidence";
    //URL失效时间
    public static final int hours = 365*24;

    /**
     * 从OSS服务器获取OSS文件的URL
     * @param filePath 本地文件绝对路径
     * @return url
     */
    public String getURLFromOSSService(String filePath){
        try {
            // 创建OSSClient实例。
            OSS ossClient = this.createOSSClient();
            //失效时间
            // 生成URL，第一个参数为bucketName，第二个参数key为上传的文件路径名称，第三个为过期时间
            String filePathWindows = this.replaceOp(filePath);
            Date expiration = this.addDateHour(new Date(),hours);
            URL url = ossClient.generatePresignedUrl(bucketName, filePathWindows, expiration);
            //关闭
            ossClient.shutdown();
            return url.toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传文件至OSS服务器
     * @param filePath 文件绝对路径
     */
    public void upLoadFileToOSSService(String filePath){
        try{
            OSS ossClient = this.createOSSClient();
            InputStream inputStream = new FileInputStream(filePath);
            //上传日志，第一个参数为bucketName,第二个参数key为上传的文件路径名称，第三个为InputStream
            String logKey = this.replaceOp(filePath);
            ossClient.putObject(bucketName,logKey, inputStream);
            //关闭
            ossClient.shutdown();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 生成OSSclient （OSS服务端）对象
     * @return
     */
    private OSS createOSSClient(){
        // 创建ClientConfiguration实例，您可以按照实际情况修改默认参数。
        ClientBuilderConfiguration conf = new ClientBuilderConfiguration();
        // 设置是否支持CNAME。CNAME用于将自定义域名绑定到目标Bucket。
        conf.setSupportCname(true);
        // 创建OSSClient实例。
        return new OSSClientBuilder().build(endpoint, accessKeyId, secretAccessKey, conf);
    }

    /**
     * 修改文件路径格式，不能出现“/”，采用""替代
     * @param unixFilePath
     * @return
     */
    private String replaceOp(String unixFilePath){
        return unixFilePath.replace("/","");
    }

    /**
     * 获取hours小时后的日期
     * @param nowDate
     * @param hours
     * @return
     */
    private Date addDateHour(Date nowDate,int hours){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
