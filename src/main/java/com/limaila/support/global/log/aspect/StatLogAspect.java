package com.limaila.support.global.log.aspect;

import com.alibaba.fastjson.JSON;
import com.limaila.support.global.log.annotation.StatLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
@Aspect
@Slf4j
public class StatLogAspect {

    @Around(value = "@annotation(statLog)")
    public Object around(ProceedingJoinPoint point, StatLog statLog) throws Throwable {
        Object result;
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        log.info("statLog | {} | {} | {} | {} | {}", method.getDeclaringClass().getName(), method.getName() , method.getParameters().toString(), "N/A", "before");
        result = point.proceed();
        log.info("statLog | {} | {} | {} | {} | {}", method.getDeclaringClass().getName(), method.getName() , method.getParameters().toString(), JSON.toJSONString(result), "after");
        return result;
    }
}
