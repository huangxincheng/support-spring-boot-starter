package com.limaila.support.global.log.aspect;

import com.alibaba.fastjson.JSON;
import com.limaila.support.global.log.annotation.StatLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
@Aspect
@Slf4j
@Component
public class StatLogAspect {

    /**
     * @annotation 只能拦截method 不能拦截class
     */
    @Around(value = "@annotation(statLog)")
    public Object around(ProceedingJoinPoint point, StatLog statLog) throws Throwable {
        Object result;
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        String argsNameValue = this.getArgsNameValue(methodSignature.getParameterNames(), point.getArgs());
        log.info("| statLog | class={} | method={} | params={} | returnValue={} | execType={} | execTimeMillis={}", method.getDeclaringClass().getName(), method.getName() , argsNameValue, "N/A", "before", -1);
        long beforeTimeMillis = System.currentTimeMillis();
        result = point.proceed();
        log.info("| statLog | class={} | method={} | params={} | returnValue={} | execType={} | execTimeMillis={}", method.getDeclaringClass().getName(), method.getName() , argsNameValue, JSON.toJSONString(result), "after", System.currentTimeMillis() - beforeTimeMillis);
        return result;
    }

    /**
     * 获取参数名和值
     * @param names names
     * @param values values
     * @return
     */
    private String getArgsNameValue(String[] names, Object[] values) {
        StringBuilder builder = new StringBuilder();
        if (names != null && names.length > 0) {
            for (int i = 0; i < names.length; i++) {
                builder.append(names[i] + "=" + JSON.toJSONString(values[i]) + ",");
            }
        }
        return builder.toString();
    }
}
