package com.limaila.support.global.handler.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalHandler {

    /**
     * 是否统一响应
     * @return true
     */
    boolean handler() default true;

}
