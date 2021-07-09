package com.project.evidence.userModule;

import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("userAdmController")
@CrossOrigin
@RequestMapping(value = "/userAdmController",produces="text/html;charset=utf-8")
public class AdmController {
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
     * 查询全部，返回json字符串 eg:{code:"xxx",item:[{uid:1，jid:2,username:xxx,password:xxxx,
     *      truename:xxx,idcardNumber=xxxx,authority:yyyy,identify:aaaa}]}
     * @param token
     * @return
     */
    @Authority(roles = {"系统管理员"},authorities  = {"低","中","高"})
    @RequestMapping("/selectAll")
    @ResponseBody
    public String selectAll(@RequestParam("token")String token){
        Map<String,Object> resultMap = new HashMap<>();
        List<user> users = userService.getAll();
        if(users!=null && users.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",users);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.NotHasUser.code);
            resultMap.put("item",new user());
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键查询，返回json字符串 eg:{code:"xxx",item:{uid:1，jid:2,username:xxx,password:xxxx,
     * truename:xxx,idcardNumber=xxxx,authority:yyyy,identify:aaaa}}
     * @param token
     * @param uid
     * @return
     */
    @RequestMapping("/selectByUid")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"低","中","高"})
    public String selectByUID(@RequestParam("token")String token,
                              @RequestParam("uid")int uid){
        Map<String,Object> resultMap = new HashMap<>();
        user u= userService.selectByPrimaryKey(uid);
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
     * 按用户的用户名查询，返回json字符串 eg:{code:"xxx",item:{uid:1，jid:2,username:xxx,password:xxxx,
     *      truename:xxx,idcardNumber=xxxx,authority:yyyy,identify:aaaa}}
     * @param token
     * @param username
     * @return
     */
    @RequestMapping("/selectByUserName")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"低","中","高"})
    public String selectByUserName(@RequestParam("token")String token,
                                   @RequestParam("username")String username){
        Map<String,Object> resultMap = new HashMap<>();
        user u= userService.selectByUserName(username);
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
     * 按用户的身份证号查询，返回json字符串 eg:{code:"xxx",item:{uid:1，jid:2,username:xxx,password:xxxx,
     *      truename:xxx,idcardNumber=xxxx,authority:yyyy,identify:aaaa}}
     * @param token
     * @param idcardNumber
     * @return
     */
    @RequestMapping("/selectByIdCardNumber")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"低","中","高"})
    public String selectByidcardNumber(@RequestParam("token")String token,
                                   @RequestParam("idcardNumber")String idcardNumber){
        Map<String,Object> resultMap = new HashMap<>();
        user u= userService.selectByIdcardNumber(idcardNumber);
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
     * 更新，返回json字符串 eg:{code:"xxx"}
     * @param token
     * @param uid
     * @param jid
     * @param username
     * @param password
     * @param truename
     * @param idcardNumber
     * @param authority
     * @param role
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"高"})
    public String update(@RequestParam("token")String token,
                         @RequestParam("uid")int uid,
                         @RequestParam("jid")int jid,
                         @RequestParam("username")String username,
                         @RequestParam("password")String password,
                         @RequestParam("truename")String truename,
                         @RequestParam("idcardNumber")String idcardNumber,
                         @RequestParam("authority")String authority,
                         @RequestParam("identity")String role){
        Map<String,Object> resultMap = new HashMap<>();
        if(username!=null && password!=null && truename!=null
                && idcardNumber!=null && authority!=null  && role!=null
                && userService.selectByPrimaryKey(uid)!=null
                && organizationService.selectByPrimaryKey(jid)!=null) {
            if(UserUtils.checkIdCardNumber(idcardNumber)){
                if(authority.equals("低")||authority.equals("中")||authority.equals("高")){
                    if(role.equals("消防人员")||role.equals("司法人员")||role.equals("技术服务人员")
                    ||role.equals("鉴定机构人员")||role.equals("系统管理员")){
                        if(!(role.equals("系统管理员") && authority.equals("高"))) {
                            //插入
                            loginService.unlogin(uid);
                            user u = new user();
                            u.setUid(uid);
                            u.setJid(jid);
                            u.setUsername(username);
                            u.setPassword(password);
                            u.setTruename(truename);
                            u.setIdcardnumber(idcardNumber);
                            u.setAuthority(authority);
                            u.setIdentity(role);
                            if (userService.updateByPrimaryKey(u)) {
                                //成功
                                resultMap.put("code", ErrorCode.UpdateSuccess.code);
                                return JSONObject.toJSONString(resultMap);
                            } else {
                                resultMap.put("code", ErrorCode.UpdateFail.code);
                                return JSONObject.toJSONString(resultMap);
                            }
                        }else{
                            //身份错误，不能修改为高级管理员
                            resultMap.put("code",ErrorCode.RoleError.code);
                            return JSONObject.toJSONString(resultMap);
                        }
                    }else{
                        //身份错误
                        resultMap.put("code",ErrorCode.RoleError.code);
                        return JSONObject.toJSONString(resultMap);
                    }
                }else{
                    //权限错误
                    resultMap.put("code",ErrorCode.AuthorityError.code);
                    return JSONObject.toJSONString(resultMap);
                }
            }else{
                //身份证格式错误
                resultMap.put("code",ErrorCode.IdcardNumberError.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //属性错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 插入，返回json字符串 eg:{code:"xxx"}
     * @param token
     * @param jid
     * @param username
     * @param password
     * @param truename
     * @param idcardNumber
     * @param authority
     * @param role
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"高"})
    public String insert(@RequestParam("token")String token,
                         @RequestParam("jid")int jid,
                         @RequestParam("username")String username,
                         @RequestParam("password")String password,
                         @RequestParam("truename")String truename,
                         @RequestParam("idcardNumber")String idcardNumber,
                         @RequestParam("authority")String authority,
                         @RequestParam("identity")String role){
        Map<String,Object> resultMap = new HashMap<>();
        if(username!=null && password!=null && truename!=null
                && idcardNumber!=null && authority!=null  && role!=null
                && organizationService.selectByPrimaryKey(jid)!=null) {
            if(UserUtils.checkIdCardNumber(idcardNumber)){
                if(authority.equals("低")||authority.equals("中")||authority.equals("高")){
                    if(role.equals("消防人员")||role.equals("司法人员")||role.equals("技术服务人员")
                            ||role.equals("鉴定机构人员")||role.equals("系统管理员")){
                        //插入
                        user u = new user();
                        u.setJid(jid);
                        u.setUsername(username);
                        u.setPassword(password);
                        u.setTruename(truename);
                        u.setIdcardnumber(idcardNumber);
                        u.setAuthority(authority);
                        u.setIdentity(role);
                        if(userService.insert(u)){
                            //成功
                            resultMap.put("code",ErrorCode.InsertSuccess.code);
                            return JSONObject.toJSONString(resultMap);
                        }else{
                            resultMap.put("code",ErrorCode.InsertFail.code);
                            return JSONObject.toJSONString(resultMap);
                        }
                    }else{
                        //身份错误
                        resultMap.put("code",ErrorCode.RoleError.code);
                        return JSONObject.toJSONString(resultMap);
                    }
                }else{
                    //权限错误
                    resultMap.put("code",ErrorCode.AuthorityError.code);
                    return JSONObject.toJSONString(resultMap);
                }
            }else{
                //身份证格式错误
                resultMap.put("code",ErrorCode.IdcardNumberError.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //属性错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键删除，返回json字符串 eg:{code:"xxx"}
     * @param token
     * @param uid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"高"})
    public String delete(@RequestParam("token")String token,
                         @RequestParam("uid")int uid){
        Map<String,Object> resultMap = new HashMap<>();
        if(userService.selectByPrimaryKey(uid)!=null &&
                !(userService.selectByPrimaryKey(uid).getIdentity().equals("系统管理员") &&
                userService.selectByPrimaryKey(uid).getAuthority().equals("高"))) {
            if(userService.deleteByPrimaryKey(uid)){
                //成功
                resultMap.put("code",ErrorCode.DeleteSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                //失败
                resultMap.put("code",ErrorCode.DeleteFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //属性错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }
}
