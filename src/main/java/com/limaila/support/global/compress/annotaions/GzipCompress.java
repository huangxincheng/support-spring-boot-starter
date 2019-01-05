package com.limaila.support.global.compress.annotaions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 *
 **/
@Target(value = {ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface GzipCompress {

    /**
     * 是否压缩数据
     *  默认是
     * @return
     */
    boolean compress() default true;
}
