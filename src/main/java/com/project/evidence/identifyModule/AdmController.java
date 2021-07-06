package com.project.evidence.identifyModule;

import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.identifyModule.database.entity.identify;
import com.project.evidence.identifyModule.database.entity.identifyProvide;
import com.project.evidence.identifyModule.database.entity.identifyResult;
import com.project.evidence.userModule.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller("identifyAdmController")
@CrossOrigin
@RequestMapping(value = "/identifyAdmController",produces="text/html;charset=utf-8")
public class AdmController {

    @Autowired
    @Qualifier("userService")
    Service userService;

    @Autowired
    @Qualifier("loginService")
    com.project.evidence.loginModule.Service loginService;

    @Autowired
    @Qualifier("identifyService")
    com.project.evidence.identifyModule.Service identifyService;

    @Autowired
    @Qualifier("organizationService")
    com.project.evidence.organizationModule.Service organizationService;


    /**
     * 插入报表
     * 返回json {code='xxx'}
     * @param token
     * @param Wid
     * @param Qid
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/insertIdentify")
    @ResponseBody
    public String insertIdentify(@RequestParam("token") String token,
                                 @RequestParam("wid") int Wid,
                                 @RequestParam("qid") int Qid){
        int uid = loginService.checkToken(token);
        Map<String,Object> resultMap = new HashMap<>();
        if(uid!=-1) {
            identify i = new identify();
            i.setWid(Wid);
            i.setSubmitdate(new Date());
            i.setState("已提交");
            i.setUid(uid);
            i.setQid(Qid);
            if(identifyService.insertIdentify(i)){
                //插入成功
                resultMap.put("code",ErrorCode.InsertSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.InsertFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //用户异常
            resultMap.put("code",ErrorCode.UserError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键删除报表
     * 返回json {code='xxx'}
     * @param token
     * @param bid
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/deleteIdentify")
    @ResponseBody
    public String deleteIdentify(@RequestParam("token") String token,
                                 @RequestParam("bid") int bid){
        Map<String,Object> resultMap = new HashMap<>();
        if(identifyService.deleteIdentifyByPrimaryKey(bid)){
            resultMap.put("code",ErrorCode.DeleteSuccess.code);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.DeleteFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 插入鉴定结果
     * 返回json {code='xxx'}
     * @param token
     * @param bid
     * @param rtext
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/insertIdentifyResult")
    @ResponseBody
    public String insertIdentifyResult(@RequestParam("token") String token,
                                       @RequestParam("bid")int bid,
                                       @RequestParam("jid")int jid,
                                       @RequestParam("rtext")String rtext){

        Map<String,Object> resultMap = new HashMap<>();
        if(identifyService.selectIdentifyByPrimaryKey(bid)!=null &&
           organizationService.selectByPrimaryKey(jid)!=null) {
            identifyResult r = new identifyResult();
            r.setBid(bid);
            r.setJid(jid);
            r.setProvidedate(new Date());
            r.setRtext(rtext);
            if(identifyService.insertIdentifyResult(r)){
                //插入成功
                resultMap.put("code",ErrorCode.InsertSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.InsertFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //bid,jid错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键删除鉴定结果
     * 返回json {code='xxx'}
     * @param token
     * @param bid
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/deleteIdentifyResult")
    @ResponseBody
    public String deleteIdentifyResult(@RequestParam("token") String token,
                                       @RequestParam("bid")int bid){
        Map<String,Object> resultMap = new HashMap<>();
        if(identifyService.deleteIdentifyResultByPrimaryKey(bid)){
            resultMap.put("code",ErrorCode.DeleteSuccess.code);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.DeleteFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 插入鉴定提供单
     * 返回json {code='xxx'}
     * @param token
     * @param jid
     * @param identifyText
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/insertIdentifyProvide")
    @ResponseBody
    public String insertIdentifyProvide(@RequestParam("token") String token,
                                        @RequestParam("jid")int jid,
                                        @RequestParam("identifyText")String identifyText){
        Map<String,Object> resultMap = new HashMap<>();
        if(organizationService.selectByPrimaryKey(jid)!=null) {
            identifyProvide p = new identifyProvide();
            p.setJid(jid);
            p.setIdentifytext(identifyText);
            if(identifyService.insertIdentifyProvide(p)){
                //插入成功
                resultMap.put("code",ErrorCode.InsertSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.InsertFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //jid错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 主键删除鉴定提供单
     * 返回json {code='xxx'}
     * @param token
     * @param qid
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/deleteIdentifyProvide")
    @ResponseBody
    public String deleteIdentifyProvide(@RequestParam("token") String token,
                                        @RequestParam("qid")int qid){
        Map<String,Object> resultMap = new HashMap<>();
        if(identifyService.deleteIdentifyProvideByPrimaryKey(qid)){
            resultMap.put("code",ErrorCode.DeleteSuccess.code);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.DeleteFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 修改鉴定提供单
     * 返回json {code='xxx'}
     * @param token
     * @param qid
     * @param identifyText
     * @return
     */
    @Authority(authorities = {"高"},roles = {"系统管理员"})
    @RequestMapping("/updateIdentifyProvide")
    @ResponseBody
    public String updateIdentifyProvide(@RequestParam("token") String token,
                                        @RequestParam("qid")int qid,
                                        @RequestParam("identifyText")String identifyText){
        Map<String,Object> resultMap = new HashMap<>();
        if(identifyService.selectIdentifyProvideByPrimaryKey(qid)!=null) {
            identifyProvide p = identifyService.selectIdentifyProvideByPrimaryKey(qid);
            p.setIdentifytext(identifyText);
            if(identifyService.updateIdentifyProvideByPrimaryKey(p)){
                //修改成功
                resultMap.put("code",ErrorCode.UpdateSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.UpdateFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            //jid错误
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按bid查询报表
     * 返回json {code='xxx',item={bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *          ,qid:'xxx',state:'xxx'}}
     * @param token
     * @param bid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyByBid")
    @ResponseBody
    public String selectIdentifyByBid(@RequestParam("token") String token,
                                      @RequestParam("bid")int bid){
        identify i = identifyService.selectIdentifyByPrimaryKey(bid);
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",i);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按wid查询报表
     * 返回json {code='xxx',item=[{bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *          ,qid:'xxx',state:'xxx'}]}
     * @param token
     * @param wid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyByWid")
    @ResponseBody
    public String selectIdentifyByWid(@RequestParam("token") String token,
                                      @RequestParam("wid")int wid){
        List<identify> i = identifyService.selectIdentifyByWid(wid);
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null && i.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",i);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按uid查询报表
     * 返回json {code='xxx',item=[{bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *          ,qid:'xxx',state:'xxx'}]}
     * @param token
     * @param uid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyByUid")
    @ResponseBody
    public String selectIdentifyByUid(@RequestParam("token") String token,
                                      @RequestParam("uid")int uid){
        List<identify> i = identifyService.selectIdentifyByUid(uid);
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null && i.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",i);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按jid查询报表
     * 返回json {code='xxx',item=[{bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *          ,qid:'xxx',state:'xxx'}]}
     * @param token
     * @param jid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyByJid")
    @ResponseBody
    public String selectIdentifyByJid(@RequestParam("token") String token,
                                      @RequestParam("jid")int jid){
        List<identifyProvide> p = identifyService.selectIdentifyProvideByJid(jid);
        Map<String, Object> resultMap = new HashMap<>();
        if(p!=null) {
            List<identify> i = new ArrayList<>();
            for(identifyProvide pi :p){
                if(identifyService.selectIdentifyByQid(pi.getQid())!=null){
                    i.addAll(identifyService.selectIdentifyByQid(pi.getQid()));
                }
            }
            if (i.size()>0) {
                resultMap.put("code", ErrorCode.SelectSuccess.code);
                resultMap.put("item", i);
                return JSONObject.toJSONString(resultMap);
            } else {
                resultMap.put("code", ErrorCode.SelectNull.code);
                resultMap.put("item", "");
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code", ErrorCode.SelectNull);
            resultMap.put("item", "");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 查询全部鉴定提供单
     * 返回json {code='xxx',item=[{qid:'xxx',jid:'xxx',identifyText:'xxx'}]}
     * @param token
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectAllIdentifyProvide")
    @ResponseBody
    public String selectAllIdentifyProvide(@RequestParam("token") String token){
        List<identifyProvide> i = identifyService.getAllProvide();
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null && i.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",i);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按jid查询鉴定提供单
     * 返回json {code='xxx',item=[{qid:'xxx',jid:'xxx',identifyText:'xxx'}]}
     * @param token
     * @param jid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyProvideByJid")
    @ResponseBody
    public String selectIdentifyProvideByJid(@RequestParam("token") String token,
                                             @RequestParam("jid")int jid){
        List<identifyProvide> i = identifyService.selectIdentifyProvideByJid(jid);
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null && i.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",i);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按qid查询鉴定提供单
     * 返回json {code='xxx',item={qid:'xxx',jid:'xxx',identifyText:'xxx'}}
     * @param token
     * @param qid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyProvideByQid")
    @ResponseBody
    public String selectIdentifyProvideByQid(@RequestParam("token") String token,
                                             @RequestParam("qid")int qid){
        identifyProvide i = identifyService.selectIdentifyProvideByPrimaryKey(qid);
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",i);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按bid查询鉴定结果
     * 返回json {code='xxx',item={bid:'xxx',jid:'xxx',rText:'xxx',provideDate:'xxx'}}
     * @param token
     * @param bid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyResultBid")
    @ResponseBody
    public String selectIdentifyResultBid(@RequestParam("token") String token,
                                          @RequestParam("bid")int bid){
        identifyResult r = identifyService.selectIdentifyResultByPrimaryKey(bid);
        Map<String,Object> resultMap = new HashMap<>();
        if(r!=null){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",r);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 按jid查询鉴定结果
     * 返回json {code='xxx',item=[{bid:'xxx',jid:'xxx',rText:'xxx',provideDate:'xxx'}]}
     * @param token
     * @param jid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"系统管理员"})
    @RequestMapping("/selectIdentifyResultByJid")
    @ResponseBody
    public String selectIdentifyResultByJid(@RequestParam("token") String token,
                                            @RequestParam("jid")int jid){
        List<identifyResult> r = identifyService.selectIdentifyResultByJid(jid);
        Map<String,Object> resultMap = new HashMap<>();
        if(r!=null && r.size()!=0){
            resultMap.put("code",ErrorCode.SelectSuccess.code);
            resultMap.put("item",r);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code",ErrorCode.SelectNull.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

}
