package com.project.evidence.loginModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

@Component
public class Utils {

    /**
     * 生成token字符串
     * @param userName
     * @return
     */
    public String createToken(String userName){
        return this.EncoderByMd5(userName + new Date().toString());
    }

    /**
     * 生成reftoken字符串
     * @param token
     * @return
     */
    public String createRefToken(String token){
        return this.EncoderByMd5(token + new Date().toString());
    }

    /**
     * 获取min分钟后的日期
     * @param nowDate
     * @param min
     * @return
     */
    Date addDateMin(Date nowDate, int min){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowDate);
        calendar.add(Calendar.MINUTE, min);
        return calendar.getTime();
    }

    /**
     * MD5进行加密
     * @param str  待加密的字符串
     * @return  加密后的字符串
     */
    private String EncoderByMd5(String str){
        try {
            if(str!=null) {
                //确定计算方法
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                Base64.Encoder encoder = Base64.getEncoder();
                //加密后的字符串
                return encoder.encodeToString(md5.digest(str.getBytes(StandardCharsets.UTF_8)));
            }else {
                return null;
            }
        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
    }

}
