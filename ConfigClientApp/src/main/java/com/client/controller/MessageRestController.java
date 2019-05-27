package com.client.controller;

import com.util.HttpRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@RestController
public class MessageRestController {

    @Value("${test}") // git配置文件里的key
    String test;
    @Value("${test2}") // git配置文件里的key
    String test2;

    @RequestMapping(value = "/hi")
    public String hi(){
        return test;
    }

    @RequestMapping(value = "/hi2")
    public String hi2(){
        return test2;
    }

    @RequestMapping(value = "/encrypt")
    public String encrypt(String key){
        System.out.println("需加密的明文:"+key);
        String result = HttpRequest.sendPost("http://localhost:8961/encrypt",key);
        System.out.println("获得密文:"+result);
        return result;
    }
}
