package com.client.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class MessageRestController {

    @Value("${test}") // git配置文件里的key
    String test;

    @RequestMapping(value = "/hi")
    public String hi(){
        return test;
    }

    @RequestMapping(value = "/hi2")
    public String hi2(){
        return "123";
    }

}
