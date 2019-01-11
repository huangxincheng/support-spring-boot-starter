package com.limaila.support;

import com.limaila.support.global.log.annotation.StatLog;
import org.springframework.aop.framework.AopContext;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
//TODO 测试代码以后可删
@Service

public class TestService {

    @StatLog
    public String sayHello(String str) {
        System.out.println("111");
//        this.say();
        // 必须@EnableAspectJAutoProxy exposeProxy=true
        ((TestService) AopContext.currentProxy()).say();
        new Thread(new Runnable() {
            @Override
            public void run() {
//                如果异步的话会报错
//                ((TestService) AopContext.currentProxy()).say();
            }
        }).start();
        return str;
    }

    /**
     * 通过自调的是没经过aspect
     */
    public void say() {
        System.out.println("121");
    }
}
