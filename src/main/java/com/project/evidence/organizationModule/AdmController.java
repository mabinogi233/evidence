package com.project.evidence.organizationModule;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.organizationModule.database.entity.organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 机构管理，仅管理员可用
 */
@Controller("organizationController")
@CrossOrigin
@RequestMapping(value = "/organization",produces="text/html;charset=utf-8")
public class AdmController {

    @Autowired
    @Qualifier("organizationService")
    Service organizationService;

    /**
     * 查询全部，返回json字符串 eg:{code:"xxx",item:[{jid:1,jname="xxxx",jtext="yyyyy"}]}
     * @param token
     * @return
     */
    @Authority(roles = {"系统管理员"},authorities  = {"低","中","高"})
    @RequestMapping("/selectAll")
    @ResponseBody
    public String selectAll(@RequestParam("token")String token){
        Map<String,Object> resultMap = new HashMap<>();
        List<organization> organizations = organizationService.getAll();
        if(organizations!=null && organizations.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",organizations);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键查询，返回json字符串 eg:{code:"xxx",item:{jid:1,jname="xxxx",jtext="yyyyy"}}
     * @param token
     * @param jid
     * @return
     */
    @RequestMapping("/select")
    @ResponseBody
    public String selectByJID(@RequestParam("token")String token,
                              @RequestParam("jid")int jid){
        Map<String,Object> resultMap = new HashMap<>();
        organization organ = organizationService.selectByPrimaryKey(jid);
        if(organ!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",organ);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.PrimaryKeyError.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 更新，返回json字符串 eg:{code:"xxx"}
     * @param token
     * @param jid
     * @param jname
     * @param jtext
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"高"})
    public String update(@RequestParam("token")String token,
                         @RequestParam("jid")int jid,
                         @RequestParam("jname")String jname,
                         @RequestParam("jtext")String jtext){
        Map<String,Object> resultMap = new HashMap<>();
        if(jname!=null && jtext!=null && organizationService.selectByPrimaryKey(jid)!=null) {
            organization o = new organization();
            o.setJid(jid);
            o.setJname(jname);
            o.setJtext(jtext);
            if(organizationService.updateByPrimaryKey(o)){
                //成功
                resultMap.put("code",ErrorCode.UpdateSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                //失败
                resultMap.put("code",ErrorCode.UpdateFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //属性错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 插入，
     * 返回json字符串 eg:{code:"xxx"}
     * @param token
     * @param jname
     * @param jtext
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"高"})
    public String insert(@RequestParam("token")String token,
                         @RequestParam("jname")String jname,
                         @RequestParam("jtext")String jtext){
        Map<String,Object> resultMap = new HashMap<>();
        if(jname!=null && jtext!=null) {
            organization o = new organization();
            o.setJname(jname);
            o.setJtext(jtext);
            if(organizationService.insert(o)){
                //成功
                resultMap.put("code",ErrorCode.InsertSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                //失败
                resultMap.put("code",ErrorCode.InsertFail.code);
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
     * @param jid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities  = {"高"})
    public String delete(@RequestParam("token")String token,
                         @RequestParam("jid")int jid){
        Map<String,Object> resultMap = new HashMap<>();
        if(organizationService.selectByPrimaryKey(jid)!=null) {
            if(organizationService.deleteByPrimaryKey(jid)){
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
