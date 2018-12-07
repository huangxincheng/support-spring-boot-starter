package com.limaila.support.global.handler;

import com.alibaba.fastjson.JSON;
import com.limaila.support.global.handler.annotation.GlobalHandler;
import com.limaila.support.global.handler.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;



/**
 * 统一响应处理
 * Author: huangxincheng
 * <p>
 * <p>
 **/
@RestControllerAdvice
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice {

    private static final ThreadLocal<Long> costTimeLocal = new ThreadLocal<Long>();

    public boolean supports(MethodParameter returnType, Class converterType) {
        boolean flag = false;
        GlobalHandler g1 = returnType.getMethodAnnotation(GlobalHandler.class);
        if (g1 != null) {
            flag = g1.handler();
        } else {
            g1 = AnnotationUtils.findAnnotation(returnType.getContainingClass(), GlobalHandler.class);
            if (g1 != null) {
                flag = g1.handler();
            }
        }
        if (flag) {
            costTimeLocal.set(System.currentTimeMillis());
        }
        log.debug("[GlobalResponseBodyHandler]=========={} = {}", "supports", flag);
        return flag;

    }

    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        try {
            if (body instanceof String) {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
                return JSON.toJSONString(GlobalResponse.toSucc(System.currentTimeMillis() - costTimeLocal.get(), body));
            } else if (body instanceof GlobalResponse) {
                ((GlobalResponse) body).getResult().setCostTime(System.currentTimeMillis() - costTimeLocal.get());
                return body;
            } else {
                return GlobalResponse.toSucc(System.currentTimeMillis() - costTimeLocal.get(), body);
            }
        } finally {
            log.debug("[GlobalResponseBodyHandler]=========={} = {}", "handler costTime", System.currentTimeMillis() - costTimeLocal.get());
            costTimeLocal.remove();
        }
    }

}
