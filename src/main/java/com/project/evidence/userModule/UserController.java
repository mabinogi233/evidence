package com.project.evidence.userModule;


import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.organizationModule.database.entity.organization;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller("userController")
@RequestMapping(value = "/userController",produces="text/html;charset=utf-8")
public class UserController {

    @Autowired
    @Qualifier("userService")
    Service userService;

    @Autowired
    @Qualifier("loginService")
    com.project.evidence.loginModule.Service loginService;

    @Autowired
    @Qualifier("organizationService")
    com.project.evidence.organizationModule.Service organizationService;

    /**
     * 主键查询，返回json字符串 eg:{code:"xxx",item:{uid:1，jid:2,username:xxx,password:xxxx,
     * truename:xxx,idcardNumber=xxxx,authority:yyyy,identify:aaaa}}
     * @param token
     * @return
     */
    @RequestMapping("/selectByUid")
    @ResponseBody
    public String selectByUID(@RequestParam("token")String token){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",u);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.NotHasUser.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 查询用户属于的机构,返回json字符串 eg:{code:"xxx",item:{jid:1,jname="xxxx",jtext="yyyyy"}}
     * @param token
     * @return
     */
    @RequestMapping("/selectOrganization")
    @ResponseBody
    public String selectOrganization(@RequestParam("token")String token){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null){
            organization o = organizationService.selectByPrimaryKey(u.getJid());
            if(o!=null) {
                resultMap.put("code", ErrorCode.SelectSuccess.code);
                resultMap.put("item", o);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.SelectFail.code);
                resultMap.put("item","");
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code",ErrorCode.NotHasUser.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按用户的身份证号查询，返回json字符串 eg:{code:"xxx",item:{uid:1，jid:2,username:xxx,password:xxxx,
     *      truename:xxx,idcardNumber=xxxx,authority:yyyy,identify:aaaa}}
     *      用于找回密码
     * @param idcardNumber
     * @return
     */
    @RequestMapping("/selectByIdCardNumber")
    @ResponseBody
    public String selectByidcardNumber(@RequestParam("idcardNumber")String idcardNumber){
        Map<String,Object> resultMap = new HashMap<>();
        user u = userService.selectByIdcardNumber(idcardNumber);
        if(u!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",u);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.NotHasUser.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 用户更新基本信息，返回json字符串 eg:{code:"xxx"}
     * @param token
     * @param jid
     * @param username 可修改
     * @param password 可修改
     * @param truename
     * @param idcardNumber
     * @param authority
     * @param role
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    public String update(@RequestParam("token")String token,
                         @RequestParam("jid")int jid,
                         @RequestParam("username")String username,
                         @RequestParam("password")String password,
                         @RequestParam("truename")String truename,
                         @RequestParam("idcardNumber")String idcardNumber,
                         @RequestParam("authority")String authority,
                         @RequestParam("identity")String role){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null){
            if(u.getJid()==jid && u.getTruename().equals(truename) &&
               u.getIdcardnumber().equals(idcardNumber) && u.getAuthority().equals(authority) &&
               u.getIdentity().equals(role)){
                if(userService.selectByUserName(username)==null){
                    if(!u.getPassword().equals(password)){
                        //修改密码后下线
                        loginService.unlogin(u.getUid());
                    }
                    u.setUsername(username);
                    u.setPassword(password);
                    if(userService.updateByPrimaryKey(u)){
                        //成功
                        resultMap.put("code",ErrorCode.UpdateSuccess.code);
                        return JSONObject.toJSONString(resultMap);
                    }else{
                        //失败
                        resultMap.put("code",ErrorCode.UpdateFail.code);
                        return JSONObject.toJSONString(resultMap);
                    }
                }else{
                    //用户名重复
                    resultMap.put("code",ErrorCode.HasUserName.code);
                    return JSONObject.toJSONString(resultMap);
                }
            }else{
                //修改了不可修改的参数值
                resultMap.put("code",ErrorCode.UpdateFixItem.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //无此用户
            resultMap.put("code",ErrorCode.NotHasUser.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

}
