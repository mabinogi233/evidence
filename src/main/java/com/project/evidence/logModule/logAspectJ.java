package com.project.evidence.logModule;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * 输出日志类
 */
@Aspect
@Component
public class logAspectJ {

    private static final Logger logger = LoggerFactory.getLogger(logAspectJ.class);

    //切点，匹配com.project.evidence包内全部Controller的全部方法
    private final String POINT_CUT = "execution(* com.project.evidence.*.*Controller.*(..))";

    @Before(value = POINT_CUT)
    public void before(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        List<Object> args = Arrays.asList(joinPoint.getArgs());
        //System.out.println("调用前连接点方法为：" + methodName + ",参数为：" + args);
        logger.info("调用前连接点方法为：" + methodName + ",参数为：" + args);
    }

    @AfterReturning(value = POINT_CUT,returning = "result")
    public void afterReturning(JoinPoint point, Object result){
        String methodName = point.getSignature().getName();
        List<Object> args = Arrays.asList(point.getArgs());
        //System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",目标方法执行结果为：" + result);
        logger.info("连接点方法为：" + methodName + ",参数为：" + args + ",目标方法执行结果为：" + result);
    }

    @AfterThrowing(value = POINT_CUT,throwing = "e")
    public void afterThrowing(JoinPoint point, Throwable e){
        String methodName = point.getSignature().getName();
        List<Object> args = Arrays.asList(point.getArgs());
        //System.out.println("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" + e);
        logger.error("连接点方法为：" + methodName + ",参数为：" + args + ",异常为：" ,e);
    }

}
