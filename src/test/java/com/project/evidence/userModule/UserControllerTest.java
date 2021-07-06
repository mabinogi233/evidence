package com.project.evidence.userModule;

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
public class UserControllerTest {

    public UserControllerTest(){}

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void selectByUID() {
        String path = "/userController/selectByUid";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        this.postTest(params,path);
    }

    @Test
    public void selectOrganization() {
        String path = "/userController/selectOrganization";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        this.postTest(params,path);
    }

    @Test
    public void selectByidcardNumber() {
        String path = "/userController/selectByIdCardNumber";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("idcardNumber", "130104200005012411");
        this.postTest(params,path);
    }

    @Test
    @Transactional
    @Rollback
    public void update() {
        String path = "/userController/update";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("token", "Rp6npIeiQ93mSFpsmeg7gA==");
        params.add("jid", "2");
        params.add("username", "ceshi用户");
        params.add("password", "12345678");
        params.add("truename", "更新测试");
        params.add("idcardNumber", "110101199003077635");
        params.add("authority", "高");
        params.add("identity", "系统管理员");
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