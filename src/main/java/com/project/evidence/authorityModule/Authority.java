package com.project.evidence.authorityModule;

import java.lang.annotation.*;

//权限与身份拦截
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authority {

    String[] value() default {};
    //身份
    String[] roles() default {};
    //权限
    String[] authorities() default {};

}

