package com.project.evidence.evidenceModule;

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
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    public AdmControllerTest(){}

    @Test
    public void selectAll() {
        String path = "/evidenceAdm/selectAll";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        this.postTest(params,path);
    }

    @Test
    public void selectByUid() {
        String path = "/evidenceAdm/selectByUid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("uid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectByText() {
        String path = "/evidenceAdm/selectByText";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("text", "测试");
        this.postTest(params,path);
    }

    @Test
    public void selectByWid() {
        String path = "/evidenceAdm/selectByWid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("wid", "0");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void update() {
        String path = "/evidenceAdm/update";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("wid", "0");
        params.add("wtext", "物证已损坏");
        params.add("state", "异常");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void insert() {
        String path = "/evidenceAdm/insert";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("uid", "0");
        params.add("wtext", "物证A");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void delete() {
        String path = "/evidenceAdm/delete";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("wid", "0");
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