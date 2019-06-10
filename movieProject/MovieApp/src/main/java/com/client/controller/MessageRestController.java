package com.client.controller;

import com.util.HttpRequest;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RefreshScope
@Controller
public class MessageRestController {

    private Logger logger = Logger.getLogger(MessageRestController.class);

    @RequestMapping(value = "/encrypt")
    public String encrypt(String key){
        System.out.println("需加密的明文:"+key);
        String result = HttpRequest.sendPost("http://localhost:8961/encrypt",key);
        System.out.println("获得密文:"+result);
        return result;
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String login() {
        logger.info("=====");
        return "/index";
    }
    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test() {
        logger.info("test=====");
        return "/test";
    }
}
