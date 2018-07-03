package com.ms.spring.cloud.springclouddemo.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Sherlock
 * @Description:
 * @Date: Created in 10:05 2018/7/3
 * @Modified By:
 */
@RestController
public class HelloController {

    //@Autowired
    //private CounterService counterService;

    @RequestMapping("/hello")
    public String hello() {

        return "Hello World!!!";
    }
}
