package com.project.evidence.userModule;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
    public void selectAll() {
        String path = "/userAdmController/selectAll";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        this.postTest(params,path);
    }

    @Test
    public void selectByUID() {
        String path = "/userAdmController/selectByUid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("uid", "0");
        this.postTest(params,path);
    }

    @Test
    public void selectByUserName() {
        String path = "/userAdmController/selectByUserName";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("username", "ceshi");
        this.postTest(params,path);
    }

    @Test
    public void selectByidcardNumber() {
        String path = "/userAdmController/selectByIdCardNumber";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("idcardNumber", "130104158312041234");
        this.postTest(params,path);
    }

    @Test
    public void update() {
        String path = "/userAdmController/update";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("uid", "1");
        params.add("jid", "2");
        params.add("username", "update??????");
        params.add("password", "123456");
        params.add("truename", "??????");
        params.add("idcardNumber", "130104200005012411");
        params.add("authority", "???");
        params.add("identity", "???????????????");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void insert() {
        String path = "/userAdmController/insert";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("jid", "2");
        params.add("username", "insert??????");
        params.add("password", "123456");
        params.add("truename", "insert??????");
        params.add("idcardNumber", "110101199003078312");
        params.add("authority", "???");
        params.add("identity", "???????????????");
        this.postTest(params,path);
    }

    @Test
    @Rollback
    @Transactional
    public void delete() {
        String path = "/userAdmController/delete";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("uid", "2");
        this.postTest(params,path);
    }

    /**
     * get ????????????
     * @param params
     * @param path
     * @return
     * @throws Exception
     */
    public Object getTest(MultiValueMap<String, String> params,String path) {
        MvcResult result =null;
        try {
            System.out.println("????????????:"+params);
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
                System.out.println("????????????:" + result.getResponse().getContentAsString());
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
     * post ????????????
     * @param params
     * @param path
     * @return
     * @throws Exception
     */
    public Object postTest(MultiValueMap<String, String> params,String path) {
        MvcResult result =null;
        try {
            System.out.println("????????????:"+params);
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
                System.out.println("????????????:" + result.getResponse().getContentAsString(StandardCharsets.UTF_8));
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
     * ??????????????????
     * @param erro
     */
    public void erro(String erro){
        if (erro.contains("404")){
            System.out.println("??????url??????:"+404);
        }else if (erro.contains("405")){
            System.out.println("??????????????????:"+405);
        }else {
            System.out.println("???????????????:"+erro);
        }
    }

}