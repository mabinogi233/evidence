package com.project.evidence.authorityModule;


import com.alibaba.fastjson.JSONObject;
import com.project.evidence.loginModule.Service;
import com.project.evidence.userModule.database.entity.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

//权限拦截器
@Component
public class AuthorityHandle implements HandlerInterceptor {

    @Autowired
    @Qualifier("loginService")
    Service loginService;

    @Autowired
    @Qualifier("userService")
    com.project.evidence.userModule.Service userService;

    // 在调用方法之前执行拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //首先进行身份验证
        String token = request.getParameter("token");
        if(token==null){
            return true;
        }
        int uid = loginService.checkToken(token);
        if(uid==-1){
            //身份验证失败，token无效
            Map<String,String> resultMap = new HashMap<>();
            resultMap.put("code",ErrorCode.TokenError.code);
            //返回json
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json; charset=utf-8");
            Writer w = response.getWriter();
            w.write(JSONObject.toJSONString(resultMap));
            return false;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //取出调用的方法
        Method method = handlerMethod.getMethod();
        //取出Authority注解
        Authority authority = method.getAnnotation(Authority.class);
        if (authority == null) {
            //无需权限和身份
            return true;
        }
        if (authority.authorities().length > 0 && authority.roles().length > 0) {
            // 如果权限配置不为空, 则进行验证
            String[] authorities = authority.authorities();
            String[] roles = authority.roles();
            //验证token
            user u = userService.selectByPrimaryKey(uid);
            if(u==null){
                //token无效
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("code",ErrorCode.TokenError.code);
                //返回json
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                Writer w = response.getWriter();
                w.write(JSONObject.toJSONString(resultMap));
                return false;
            }
            //权限
            String userAuth = u.getAuthority();
            boolean authSuccess = false;
            //身份
            String role = u.getIdentity();
            boolean roleSuccess = false;
            //身份认证
            for (String ro : roles) {
                if(ro.equals(role)){
                    roleSuccess = true;
                    break;
                }
            }
            if(!roleSuccess){
                //身份不匹配
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("code",ErrorCode.RoleError.code);
                //返回json
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                Writer w = response.getWriter();
                w.write(JSONObject.toJSONString(resultMap));
                return false;
            }
            //权限认证
            for (String au : authorities) {
                if(au.equals(userAuth)){
                    authSuccess = true;
                    break;
                }
            }
            if(!authSuccess){
                //权限不满足
                Map<String,String> resultMap = new HashMap<>();
                resultMap.put("code",ErrorCode.AuthorityError.code);
                //返回json
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json; charset=utf-8");
                Writer w = response.getWriter();
                w.write(JSONObject.toJSONString(resultMap));
                return false;
            }
            return true;
        }
        // 拦截之后应该返回公共结果, 这里没做处理
        return false;
    }

}