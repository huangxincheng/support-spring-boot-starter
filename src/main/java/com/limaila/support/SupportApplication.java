package com.limaila.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author: huangxincheng
 * <p>
 * <p>
 **/
@SpringBootApplication
@Controller
//TODO 测试代码以后可删
public class SupportApplication {

    public static void main(String[] args) {
        SpringApplication.run(SupportApplication.class, args);
    }

    @Autowired
    private TestService testService;

    @RequestMapping("/index")
    @ResponseBody
    public String index() {
        return testService.sayHello("嘿嘿");
    }
}
