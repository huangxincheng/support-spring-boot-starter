package com.limaila.support.global.handler;

import com.alibaba.fastjson.JSON;
import com.limaila.support.global.LocalHolder;
import com.limaila.support.global.gzip.annotation.GzipCompress;
import com.limaila.support.global.handler.annotation.GlobalHandler;
import com.limaila.support.global.handler.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
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
        boolean isGlobalHandlerResponseBody = false;
        GlobalHandler g1 = returnType.getMethodAnnotation(GlobalHandler.class);
        if (g1 != null) {
            isGlobalHandlerResponseBody = g1.handler();
        } else {
            g1 = AnnotationUtils.findAnnotation(returnType.getContainingClass(), GlobalHandler.class);
            if (g1 != null) {
                isGlobalHandlerResponseBody = g1.handler();
            }
        }

        boolean isGzipCompress = false;
        GzipCompress gc = returnType.getMethodAnnotation(GzipCompress.class);
        if (gc != null) {
            isGzipCompress = gc.compress();
        } else {
            gc = AnnotationUtils.findAnnotation(returnType.getContainingClass(), GzipCompress.class);
            if (gc != null) {
                isGzipCompress = gc.compress();
            }
        }
//        LocalHolder..set(isGzipCompress);
        LocalHolder.GZIPCOMPRESSLOCAL.set(isGzipCompress);
        if (isGlobalHandlerResponseBody) {
            costTimeLocal.set(System.currentTimeMillis());
        }
        log.debug("[GlobalResponseBodyHandler]=========={} = {}, {} = {}",
                "isGlobalHandlerResponseBody", isGlobalHandlerResponseBody,
                "isGzipCompress", isGzipCompress
        );
        return isGlobalHandlerResponseBody;

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
