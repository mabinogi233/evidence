package com.project.evidence.instrumentModule;


import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.instrumentModule.database.entity.instrument;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("instrumentController")
@CrossOrigin
@RequestMapping(value = "/instrumentController",produces="text/html;charset=utf-8")
public class UserController {

    @Autowired
    @Qualifier("instrumentService")
    Service instrumentService;

    @Autowired
    @Qualifier("loginService")
    com.project.evidence.loginModule.Service loginService;

    @Autowired
    @Qualifier("userService")
    com.project.evidence.userModule.Service userService;

    /**
     * 查询jid管理的仪器
     * 返回json，{code:xxx,item=[{yid:xxx,yText:xxx,jid:xxx,insertDate:xxx,state:xxx}]}
     * @param token
     * @return
     */
    @RequestMapping("/selectByJid")
    @ResponseBody
    @Authority(roles = {"鉴定机构人员"},authorities = {"低","中","高"})
    public String selectByUid(@RequestParam("token") String token){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null && u.getJid()!=null){
            List<instrument> instruments = instrumentService.selectByJid(u.getJid());
            if(instruments!=null && instruments.size()!=0){
                resultMap.put("code",ErrorCode.SelectSuccess.code);
                resultMap.put("item",instruments);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.SelectNull.code);
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
     * 主键查询
     * 返回json，{code:xxx,item={yid:xxx,yText:xxx,jid:xxx,insertDate:xxx,state:xxx}}
     * @param token
     * @param yid
     * @return
     */
    @RequestMapping("/selectByYid")
    @ResponseBody
    @Authority(roles = {"鉴定机构人员"},authorities = {"低","中","高"})
    public String selectByYid(@RequestParam("token") String token,
                              @RequestParam("yid") int yid){
        Map<String,Object> resultMap = new HashMap<>();
        instrument instru = instrumentService.selectByYid(yid);
        if(instru!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",instru);
            return JSONObject.toJSONString(resultMap);
        }else{
            //空
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 更仪器信息
     * 返回json，{code:xxx}
     * @param token
     * @param yText
     * @return
     */
    @RequestMapping("/update")
    @ResponseBody
    @Authority(roles = {"鉴定机构人员"},authorities = {"高"})
    public String update(@RequestParam("token") String token,
                         @RequestParam("yid") int yid,
                         @RequestParam("ytext")String yText,
                         @RequestParam("state")String state){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null && u.getJid()!=null){
            instrument i = instrumentService.selectByYid(yid);
            if(i!=null){
                i.setYtext(yText);
                if(state.equals("正常")||state.equals("损坏")) {
                    i.setState(state);
                }
                if(instrumentService.updateByPrimaryKey(i)){
                    resultMap.put("code",ErrorCode.UpdateSuccess.code);
                    return JSONObject.toJSONString(resultMap);
                } else {
                    resultMap.put("code",ErrorCode.UpdateFail.code);
                    return JSONObject.toJSONString(resultMap);
                }
            }else{
                resultMap.put("code",ErrorCode.NotHasInstrument.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code",ErrorCode.NotHasUser.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 创建仪器记录
     * 返回json，{code:xxx}
     * @param token
     * @param yText
     * @return
     */
    @RequestMapping("/insert")
    @ResponseBody
    @Authority(roles = {"鉴定机构人员"},authorities = {"高"})
    public String insert(@RequestParam("token") String token,
                         @RequestParam("ytext")String yText){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null && u.getJid()!=null){
            instrument i = new instrument();
            i.setJid(u.getJid());
            i.setYtext(yText);
            i.setInsertdate(new Date());
            i.setState("正常");
            if(instrumentService.insert(i)){
                resultMap.put("code",ErrorCode.InsertSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.InsertFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code",ErrorCode.NotHasUser.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 删除物证
     * 返回json，{code:xxx}
     * @param token
     * @param yid
     * @return
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Authority(roles = {"鉴定机构人员"},authorities = {"高"})
    public String delete(@RequestParam("token") String token,
                         @RequestParam("yid") int yid){
        Map<String,Object> resultMap = new HashMap<>();
        if(instrumentService.deleteByPrimaryKey(yid)){
            resultMap.put("code",ErrorCode.DeleteSuccess.code);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.DeleteFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }
}
