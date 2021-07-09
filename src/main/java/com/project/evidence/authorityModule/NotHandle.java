package com.project.evidence.authorityModule;


import java.lang.annotation.*;


/**
 * 无需拦截，配置后的方法无需验证token authority与roles
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NotHandle {
    String[] value() default {};
}
