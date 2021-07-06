package com.project.evidence.identifyModule;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdmControllerTest {

    public AdmControllerTest(){}

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Rollback
    @Transactional
    public void insertIdentify() {
        String path = "/identifyAdmController/insertIdentify";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("wid", "0");
        params.add("qid", "0");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void deleteIdentify() {
        String path = "/identifyAdmController/deleteIdentify";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("bid", "0");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void insertIdentifyResult() {
        String path = "/identifyAdmController/insertIdentifyResult";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("bid", "1");
        params.add("rtext", "A鉴定完毕");
        params.add("jid", "3");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void deleteIdentifyResult() {
        String path = "/identifyAdmController/deleteIdentifyResult";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("bid", "0");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void insertIdentifyProvide() {
        String path = "/identifyAdmController/insertIdentifyProvide";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("jid", "3");
        params.add("identifyText", "鉴定条目插入测试");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void deleteIdentifyProvide() {
        String path = "/identifyAdmController/insertIdentifyProvide";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("qid", "0");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void updateIdentifyProvide() {
        String path = "/identifyAdmController/updateIdentifyProvide";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("qid", "0");
        params.add("identifyText", "鉴定条目更新测试");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyByBid() {
        String path = "/identifyAdmController/selectIdentifyByBid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("bid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyByWid() {
        String path = "/identifyAdmController/selectIdentifyByWid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("wid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyByUid() {
        String path = "/identifyAdmController/selectIdentifyByUid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("uid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyByJid() {
        String path = "/identifyAdmController/selectIdentifyByJid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("jid", "2");
        this.postTest(params,path);
    }

    @Test
    public void selectAllIdentifyProvide() {
        String path = "/identifyAdmController/selectAllIdentifyProvide";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyProvideByJid() {
        String path = "/identifyAdmController/selectIdentifyProvideByJid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("jid", "2");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyProvideByQid() {
        String path = "/identifyAdmController/selectIdentifyProvideByQid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("qid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyResultBid() {
        String path = "/identifyAdmController/selectIdentifyResultBid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("bid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectIdentifyResultByJid() {
        String path = "/identifyAdmController/selectIdentifyResultByJid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("jid", "2");
        this.postTest(params,path);
    }

    /**
     * get 请求方式
     * @param params
     * @param path
     * @return
     * @throws Exception
     */
    public Object getTest(MultiValueMap<String, String> params, String path) {
        MvcResult result =null;
        try {
            System.out.println("传入参数:"+params);
            if (params==null||params.isEmpty()){
                result = mockMvc.perform(get(path).accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk())
                        .andReturn();
            }else {
                result = mockMvc.perform(get(path).accept("application/json;charset=UTF-8").params(params))
                        .andExpect(status().isOk())
                        .andReturn();
            }
        } catch (java.lang.AssertionError e) {
            erro(e.getMessage());
            e.printStackTrace();
            System.exit(1);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(result!=null && result.getResponse()!=null) {
                result.getResponse().setCharacterEncoding("UTF-8");
                System.out.println("返回参数:" + result.getResponse().getContentAsString());
                return result.getResponse().getContentAsString();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * post 请求方式
     * @param params
     * @param path
     * @return
     * @throws Exception
     */
    public Object postTest(MultiValueMap<String, String> params,String path) {
        MvcResult result =null;
        try {
            System.out.println("传入参数:"+params);
            if (params==null||params.isEmpty()){
                result = mockMvc.perform(post(path).accept("application/json;charset=UTF-8"))
                        .andExpect(status().isOk())
                        .andReturn();
            }else {
                mockMvc.perform(post(path).accept("application/json;charset=UTF-8").params(params));
                result = mockMvc.perform(post(path).params(params))
                        .andExpect(status().isOk())
                        .andReturn();

            }
        } catch (java.lang.AssertionError e) {
            erro(e.getMessage());
            System.exit(1);
        } catch (Exception e){
            e.printStackTrace();
        }
        try {
            if(result!=null && result.getResponse()!=null) {
                System.out.println("返回参数:" + result.getResponse().getContentAsString(StandardCharsets.UTF_8));
                return result.getResponse().getContentAsString();
            }else{
                return null;
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 异常类型判断
     * @param erro
     */
    public void erro(String erro){
        if (erro.contains("404")){
            System.out.println("请求url异常:"+404);
        }else if (erro.contains("405")){
            System.out.println("请求方式异常:"+405);
        }else {
            System.out.println("未编辑异常:"+erro);
        }
    }
}