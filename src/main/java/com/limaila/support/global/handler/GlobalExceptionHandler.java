package com.limaila.support.global.handler;

import com.limaila.support.global.handler.response.GlobalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 统一异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public GlobalResponse errorHandler(Exception ex) {
        log.error("[errorHandler] ============ " ,ex);
        return GlobalResponse.toFail(ex);
    }

}
