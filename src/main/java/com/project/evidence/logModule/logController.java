package com.project.evidence.logModule;


import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;
import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.log.Log;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.authorityModule.NotHandle;
import com.project.evidence.loginModule.Service;
import org.jdom2.Document;
import org.jdom2.Element;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import java.io.File;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.XMLFormatter;

/**
 * 远程获取日志
 */
@Controller("logController")
@RequestMapping(value = "/log",produces="text/html;charset=utf-8")
@PropertySource("classpath:application.properties")
@CrossOrigin
public class logController {

    @Autowired
    @Qualifier("ossService")
    private OSSService ossService;

    @Value("${logging.file.path}")
    private String LogPath;

    @Autowired
    @Qualifier("loginService")
    private Service service;

    @RequestMapping("/getLog")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"高"})
    public String getLog(@RequestParam("token")String token){
        Map<String, Object> resultMap = new HashMap<>();
        System.out.println(LogPath);
        try {
            String url = "";
            String key = "";
            LocalDate latestDate = LocalDate.MIN;
            File rootPath = new File(LogPath);
            if (rootPath.isDirectory() && rootPath.listFiles() != null) {
                //找出.log后缀的项目
                for (File f : rootPath.listFiles()) {
                    if (f.getName().contains(".")) {
                        String hz = f.getName().split("\\.")[1];
                        if (hz != null && hz.equals("log")) {
                            //找出最近的日志
                            String dateStr = f.getName().split("_")[1].split("\\.")[0];
                            System.out.println(dateStr);
                            LocalDate date = LocalDate.parse(dateStr);
                            if(date.isAfter(latestDate)){
                                latestDate = date;
                                key = f.getAbsolutePath();
                            }
                        }
                    }
                }
            }
            if(!key.equals("")) {
                ossService.upLoadFileToOSSService(key);
                url = ossService.getURLFromOSSService(key);
                resultMap.put("code", ErrorCode.GET_LOG_SUCCESS.code);
                resultMap.put("item", url);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.GET_LOG_FAIL.code);
                resultMap.put("item", "");
                return JSONObject.toJSONString(resultMap);
            }
        }catch (Exception e){
            resultMap.put("code",ErrorCode.GET_LOG_FAIL.code);
            resultMap.put("item", "");
            return JSONObject.toJSONString(resultMap);
        }
    }

}
