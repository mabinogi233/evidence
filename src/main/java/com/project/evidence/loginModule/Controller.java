package com.project.evidence.loginModule;


import com.alibaba.fastjson.JSONObject;
import com.project.evidence.loginModule.database.entity.token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@org.springframework.stereotype.Controller("loginController")
@RequestMapping(value = "/loginController",produces="text/html;charset=utf-8")
public class Controller {

    @Autowired
    @Qualifier("loginService")
    Service loginService;


    /**
     * 登录
     * 返回json，{code:xxx,token:yyy,refToken:yyy}
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String userName,
                        @RequestParam("password") String password){
        int uid = loginService.login(userName,password);
        Map<String,Object> resultMap = new HashMap<>();
        if(uid!=-1){
            //登录成功
            token t = loginService.selectByUid(uid);
            if(t!=null) {
                resultMap.put("code",ErrorCode.LoginSuccess.code);
                resultMap.put("token",t.getToken());
                resultMap.put("refToken",t.getReftoken());
                return JSONObject.toJSONString(resultMap);
            }else{
                //系统错误
                resultMap.put("code",ErrorCode.SysError.code);
                resultMap.put("token","");
                resultMap.put("refToken","");
                return JSONObject.toJSONString(resultMap);
            }
        }else {
            //登录失败
            resultMap.put("code",ErrorCode.LoginFail.code);
            resultMap.put("token","");
            resultMap.put("refToken","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 退出登录
     * 返回json，{code:xxx}
     * @param token
     * @return
     */
    @RequestMapping("/unLogin")
    @ResponseBody
    public String unlogin(String token){
        int uid = loginService.checkToken(token);
        Map<String,Object> resultMap = new HashMap<>();
        if(uid!=-1 && loginService.unlogin(uid)) {
            resultMap.put("code",ErrorCode.UnLoginSuccess.code);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.UnLoginFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }
}
