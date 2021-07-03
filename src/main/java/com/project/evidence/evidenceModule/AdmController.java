package com.project.evidence.evidenceModule;

import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.evidenceModule.database.entity.evidence;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("evidenceAdmController")
@RequestMapping(value = "/evidenceAdm",produces="text/html;charset=utf-8")
public class AdmController {

    @Autowired
    @Qualifier("evidenceService")
    Service evidenceService;

    @Autowired
    @Qualifier("userService")
    com.project.evidence.userModule.Service userService;

    @Autowired
    @Qualifier("loginService")
    com.project.evidence.loginModule.Service loginService;

    /**
     * 查询全部物证
     * 返回json，{code:xxx,item=[{wid:xxx,wText:xxx,uid:xxx,insertDate:xxx,state:xxx}]}
     * @param token
     * @return
     */
    @RequestMapping("/selectAll")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"低","中","高"})
    public String selectAll(@RequestParam("token") String token){
        Map<String,Object> resultMap = new HashMap<>();
        List<evidence> evidences =  evidenceService.selectAll();
        if(evidences!=null && evidences.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",evidences);
            return JSONObject.toJSONString(resultMap);
        }else{
            //空
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 查询uid提交的物证
     * 返回json，{code:xxx,item=[{wid:xxx,wText:xxx,uid:xxx,insertDate:xxx,state:xxx}]}
     * @param token
     * @param uid
     * @return
     */
    @RequestMapping("/selectByUid")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"低","中","高"})
    public String selectByUid(@RequestParam("token") String token,
                              @RequestParam("uid")int uid){
        Map<String,Object> resultMap = new HashMap<>();
        List<evidence> evidences =  evidenceService.selectByUid(uid);
        if(evidences!=null && evidences.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",evidences);
            return JSONObject.toJSONString(resultMap);
        }else{
            //空
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 根据物证描述查询物证
     * 返回json，{code:xxx,item=[{wid:xxx,wText:xxx,uid:xxx,insertDate:xxx,state:xxx}]}
     * @param token
     * @param text
     * @return
     */
    @RequestMapping("/selectByText")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"低","中","高"})
    public String selectByText(@RequestParam("token") String token,
                               @RequestParam("text") String text){
        Map<String,Object> resultMap = new HashMap<>();
        List<evidence> evidences =  evidenceService.selectByText(text);
        if(evidences!=null && evidences.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",evidences);
            return JSONObject.toJSONString(resultMap);
        }else{
            //空
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键查询物证
     * 返回json，{code:xxx,item={wid:xxx,wText:xxx,uid:xxx,insertDate:xxx,state:xxx}}
     * @param token
     * @param wid
     * @return
     */
    @RequestMapping("/selectByWid")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"低","中","高"})
    public String selectByWid(@RequestParam("token") String token,
                              @RequestParam("wid") int wid){
        Map<String,Object> resultMap = new HashMap<>();
        evidence evidences =  evidenceService.selectByWid(wid);
        if(evidences!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",evidences);
            return JSONObject.toJSONString(resultMap);
        }else{
            //空
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 更新物证信息
     * 返回json，{code:xxx}
     * @param token
     * @param wid
     * @param wText 可修改
     * @param state 可修改
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"高"})
    public String update(@RequestParam("token") String token,
                         @RequestParam("wid")int wid,
                         @RequestParam("wtext")String wText,
                         @RequestParam("state")String state){
        Map<String,Object> resultMap = new HashMap<>();
        evidence e = evidenceService.selectByWid(wid);
        if(e!=null){
            e.setWtext(wText);
            if(state.equals("正常")||state.equals("损坏")) {
                e.setState(state);
            }
            if(evidenceService.updateByPrimaryKey(e)){
                resultMap.put("code",ErrorCode.UpdateSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else {
                resultMap.put("code",ErrorCode.UpdateFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else {
            resultMap.put("code",ErrorCode.NotHasEvidence.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 创建新物证
     * 返回json，{code:xxx}
     * @param token
     * @param wText
     * @param uid
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"高"})
    public String insert(@RequestParam("token") String token,
                         @RequestParam("wtext")String wText,
                         @RequestParam("uid")int uid){
        Map<String,Object> resultMap = new HashMap<>();
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null){
            evidence e = new evidence();
            e.setUid(u.getUid());
            e.setState("正常");
            e.setWtext(wText);
            e.setInsertdate(new Date());
            if(evidenceService.insert(e)){
                resultMap.put("code",ErrorCode.InsertSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else {
                resultMap.put("code",ErrorCode.InsertFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else {
            resultMap.put("code",ErrorCode.NotHasUser.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 删除物证
     * 返回json，{code:xxx}
     * @param token
     * @param wid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Authority(roles = {"系统管理员"},authorities = {"高"})
    public String delete(@RequestParam("token") String token,
                         @RequestParam("wid") int wid){
        Map<String,Object> resultMap = new HashMap<>();
        if(evidenceService.deleteByPrimaryKey(wid)){
            resultMap.put("code",ErrorCode.DeleteSuccess.code);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.DeleteFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }
}
