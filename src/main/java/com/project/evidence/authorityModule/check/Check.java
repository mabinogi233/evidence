package com.project.evidence.authorityModule.check;

import com.project.evidence.authorityModule.Authority;
import org.springframework.stereotype.Component;

@Component
public class Check {
    /**
     * 身份验证
     * @param roles 方法配置的角色
     * @param role  实际请求者的角色
     * @return 是否满足角色约束
     */
    public static boolean roleCheck(String[] roles,String role){
        //身份认证
        for (String ro : roles) {
            if(ro.equals(role)){
                return true;
            }
        }
        return false;
    }

    /**
     * 权限验证
     * @param authorities 方法配置的权限
     * @param authority  实际请求者的权限
     * @return
     */
    public static boolean authorityCheck(String[] authorities,String authority){
        //权限认证
        for (String au : authorities) {
            if(au.equals(authority)){
                return true;
            }
        }
        return false;
    }
}
