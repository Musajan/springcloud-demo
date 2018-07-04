package com.ms.spring.cloud.springclouddemo.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Sherlock
 * @Description:
 * @Date: Created in 10:05 2018/7/3
 * @Modified By:
 */
@RestController
@Slf4j
public class HelloController {

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {

        ServiceInstance instance = discoveryClient.getLocalServiceInstance();
        log.info("/hello, host: " + instance.getHost() + ", service_id: " + instance.getServiceId());
        return "Hello World!!!";
    }
}
