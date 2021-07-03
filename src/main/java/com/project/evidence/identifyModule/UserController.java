package com.project.evidence.identifyModule;

import com.alibaba.fastjson.JSONObject;
import com.project.evidence.authorityModule.Authority;
import com.project.evidence.identifyModule.database.entity.identify;
import com.project.evidence.identifyModule.database.entity.identifyProvide;
import com.project.evidence.identifyModule.database.entity.identifyResult;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller("identifyUserController")
@RequestMapping(value = "/identifyUserController",produces="text/html;charset=utf-8")
public class UserController {

    @Autowired
    @Qualifier("userService")
    com.project.evidence.userModule.Service userService;

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
     * 查询全部鉴定清单
     * 返回json {code='xxx',item=[{qid:'xxx',jid:'xxx',identifyText:'xxx'}]}
     * @param token
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"消防人员","司法人员","技术服务人员","鉴定机构人员"})
    @RequestMapping("/selectAllIndentifyProvide")
    @ResponseBody
    public String selectAllIndentifyProvide(@RequestParam("token") String token){
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
     * 添加报表，此时报表状态为 已提交
     * 返回json {code='xxx'}
     * @param token
     * @param Wid
     * @param Qid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"消防人员","司法人员","技术服务人员"})
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
     * 鉴定机构人员查询提交至此机构的报表
     * 返回json {code='xxx',item=[{bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *      *          ,qid:'xxx',state:'xxx'}]}
     * @param token
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"鉴定机构人员"})
    @RequestMapping("/selectIdentifyFromJid")
    @ResponseBody
    public String selectIdentifyFromJid(@RequestParam("token") String token){
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        Map<String, Object> resultMap = new HashMap<>();
        if(u==null){
            //用户不存在
            resultMap.put("code", ErrorCode.UserError.code);
            resultMap.put("item", "");
            return JSONObject.toJSONString(resultMap);
        }
        int jid = u.getJid();
        List<identifyProvide> p = identifyService.selectIdentifyProvideByJid(jid);
        if(p!=null) {
            List<identify> i = new ArrayList<>();
            for(identifyProvide pi :p){
                i.addAll(identifyService.selectIdentifyByQid(pi.getQid()));
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
            resultMap.put("code", ErrorCode.SelectNull.code);
            resultMap.put("item", "");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 鉴定机构人员修改报表状态为 已受理，鉴定中
     * 返回json {code='xxx'}
     * @param token
     * @param bid
     * @param state
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"鉴定机构人员"})
    @RequestMapping("/setIdentifyState")
    @ResponseBody
    public String setIdentifyState(@RequestParam("token") String token,
                                        @RequestParam("bid")int bid,
                                        @RequestParam("state")String state){
        identify i = identifyService.selectIdentifyByPrimaryKey(bid);
        Map<String,Object> resultMap = new HashMap<>();
        if(i!=null){
            if((i.getState().equals("已提交")&&state.equals("已受理"))||
                    (i.getState().equals("已受理")&&state.equals("鉴定中"))){
                i.setState(state);
                if(identifyService.updateIdentifyByPrimaryKey(i)){
                    resultMap.put("code", ErrorCode.UpdateSuccess.code);
                    return JSONObject.toJSONString(resultMap);
                }else{
                    //失败
                    resultMap.put("code", ErrorCode.UpdateFail.code);
                    return JSONObject.toJSONString(resultMap);
                }
            }else {
                //禁止修改
                resultMap.put("code", ErrorCode.UpdateFixItem.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code", ErrorCode.UpdateFail.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 鉴定机构人员提交鉴定结果，并修改状态为 鉴定完毕
     * 返回json {code='xxx'}
     * @param token
     * @param bid
     * @param rtext
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"鉴定机构人员"})
    @RequestMapping("/insertIdentifyResult")
    @ResponseBody
    public String insertIdentifyResult(@RequestParam("token") String token,
                                        @RequestParam("bid")int bid,
                                        @RequestParam("rtext")String rtext){
        Map<String,Object> resultMap = new HashMap<>();
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null && identifyService.selectIdentifyByPrimaryKey(bid)!=null &&
                organizationService.selectByPrimaryKey(u.getJid())!=null) {
            identifyResult r = new identifyResult();
            r.setBid(bid);
            r.setJid(u.getJid());
            r.setProvidedate(new Date());
            r.setRtext(rtext);
            if(identifyService.insertIdentifyResult(r)){
                //插入成功
                identify i = identifyService.selectIdentifyByPrimaryKey(bid);
                i.setState("鉴定完毕");
                if(identifyService.updateIdentifyByPrimaryKey(i)) {
                    resultMap.put("code", ErrorCode.InsertSuccess.code);
                    return JSONObject.toJSONString(resultMap);
                }else{
                    identifyService.deleteIdentifyResultByPrimaryKey(bid);
                    resultMap.put("code", ErrorCode.InsertFail.code);
                    return JSONObject.toJSONString(resultMap);
                }
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
     * 按uid查询鉴定报表
     * 返回json {code='xxx',item=[{bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *   ,qid:'xxx',state:'xxx'}]}
     * @param token
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"消防人员","司法人员","技术服务人员"})
    @RequestMapping("/selectIdentifyByUid")
    @ResponseBody
    public String selectIdentifyByUid(@RequestParam("token") String token){
        int uid = loginService.checkToken(token);
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
     * 查询所属机构提交的全部报表
     * 返回json {code='xxx',item=[{bid:'xxx',wid:'xxx',submitDate:'xxx',uid:'xxx'
     *   ,qid:'xxx',state:'xxx'}]}
     * @param token
     * @return
     */
    @Authority(authorities = {"高"},roles = {"消防人员","司法人员","技术服务人员"})
    @RequestMapping("/selectIdentifyByJidFromUid")
    @ResponseBody
    public String selectIdentifyByUidSJid(@RequestParam("token") String token){
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        Map<String,Object> resultMap = new HashMap<>();
        if(u!=null && u.getJid()!=null) {
            List<user> users = userService.selectByJid(u.getJid());
            List<identify> identifies = new ArrayList<>();
            if(users!=null) {
                for (user us : users) {
                    List<identify> identifies1 = identifyService.selectIdentifyByUid(us.getUid());
                    if(identifies1!=null){
                        identifies.addAll(identifies1);
                    }
                }
                if(identifies.size()==0){
                    resultMap.put("code",ErrorCode.SelectNull.code);
                    resultMap.put("item","");
                    return JSONObject.toJSONString(resultMap);
                }else {
                    resultMap.put("code",ErrorCode.SelectSuccess.code);
                    resultMap.put("item",identifies);
                    return JSONObject.toJSONString(resultMap);
                }
            }else{
                resultMap.put("code",ErrorCode.SelectNull.code);
                resultMap.put("item","");
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code",ErrorCode.UserError.code);
            resultMap.put("item","");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 根据自身所属的jid查询机构给出的鉴定结果
     * 返回json {code='xxx',item={bid:'xxx',jid:'xxx',rText:'xxx',provideDate:'xxx'}}
     * @param token
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"鉴定机构人员"})
    @RequestMapping("/selectIdentifyResultByJid")
    @ResponseBody
    public String selectIdentifyResultByJid(@RequestParam("token") String token){
        int uid = loginService.checkToken(token);
        user u = userService.selectByPrimaryKey(uid);
        Map<String, Object> resultMap = new HashMap<>();
        if(u!=null && u.getJid()!=null) {
            List<identifyResult> r = identifyService.selectIdentifyResultByJid(u.getJid());
            if(r!=null && r.size()!=0){
                resultMap.put("code", ErrorCode.SelectSuccess.code);
                resultMap.put("item", r);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code", ErrorCode.SelectNull.code);
                resultMap.put("item", "");
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code", ErrorCode.UserError.code);
            resultMap.put("item", "");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 查询报表bid的结果
     * 返回json {code='xxx',item={bid:'xxx',jid:'xxx',rText:'xxx',provideDate:'xxx'}}
     * @param token
     * @param bid
     * @return
     */
    @Authority(authorities = {"低","中","高"},roles = {"鉴定机构人员"})
    @RequestMapping("/selectIdentifyResultByBid")
    @ResponseBody
    public String selectIdentifyResultByBid(@RequestParam("token") String token,
                                            @RequestParam("bid") int bid){
        identifyResult r = identifyService.selectIdentifyResultByPrimaryKey(bid);
        Map<String, Object> resultMap = new HashMap<>();
        if(r!=null){
            resultMap.put("code", ErrorCode.SelectSuccess.code);
            resultMap.put("item", r);
            return JSONObject.toJSONString(resultMap);
        }else{
            resultMap.put("code", ErrorCode.SelectNull.code);
            resultMap.put("item", "");
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 鉴定机构人员添加鉴定条目
     * 返回json {code='xxx'}
     * @param token
     * @param identifyText
     * @return
     */
    @Authority(authorities = {"高"},roles = {"鉴定机构人员"})
    @RequestMapping("/insertIdentifyProvide")
    @ResponseBody
    public String insertIdentifyProvide(@RequestParam("token") String token,
                                        @RequestParam("identifyText")String identifyText){
        int uid = loginService.checkToken(token);
        Map<String, Object> resultMap = new HashMap<>();
        user u = userService.selectByPrimaryKey(uid);
        if(u!=null && u.getJid()!=null && organizationService.selectByPrimaryKey(u.getJid())!=null) {
            identifyProvide p = new identifyProvide();
            p.setJid(u.getJid());
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
            resultMap.put("code",ErrorCode.UserError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

    /**
     * 鉴定机构人员删除鉴定条目
     * 返回json {code='xxx'}
     * @param token
     * @param qid
     * @return
     */
    @Authority(authorities = {"高"},roles = {"鉴定机构人员"})
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
     * 鉴定机构人员修改鉴定条目
     * 返回json {code='xxx'}
     * @param token
     * @param qid
     * @param identifyText
     * @return
     */
    @Authority(authorities = {"高"},roles = {"鉴定机构人员"})
    @RequestMapping("/updateIdentifyProvide")
    @ResponseBody
    public String updateIdentifyProvide(@RequestParam("token") String token,
                                        @RequestParam("qid")int qid,
                                        @RequestParam("identifyText")String identifyText){
        Map<String,Object> resultMap = new HashMap<>();
        identifyProvide i = identifyService.selectIdentifyProvideByPrimaryKey(qid);
        if(i!=null){
            i.setIdentifytext(identifyText);
            if(identifyService.updateIdentifyProvideByPrimaryKey(i)){
                resultMap.put("code",ErrorCode.UpdateSuccess.code);
                return JSONObject.toJSONString(resultMap);
            }else{
                resultMap.put("code",ErrorCode.UpdateFail.code);
                return JSONObject.toJSONString(resultMap);
            }
        }else{
            resultMap.put("code",ErrorCode.TextError.code);
            return JSONObject.toJSONString(resultMap);
        }
    }

}
