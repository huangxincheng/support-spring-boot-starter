package com.limaila.support;

import com.limaila.support.global.log.annotation.StatLog;
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
        this.say();
        return str;
    }

    /**
     * 通过自调的是没经过aspect
     */
    @StatLog
    public void say() {
        System.out.println("121");
    }
}
