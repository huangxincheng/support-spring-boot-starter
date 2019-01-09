package com.limaila.support.global.compress.annotaions;

import com.limaila.support.global.compress.constant.CompressConstant;

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
public @interface DataCompress {

    /**
     * 是否压缩数据 默认是
     */
    boolean compress() default true;

    /**
     * 压缩类型 默认使用GZIP进行压缩
     * @return
     */
    String type() default CompressConstant.GZIP;
}
