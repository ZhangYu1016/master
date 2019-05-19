package com.server.refresh;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

@Controller
public class RefreshController {

    @RequestMapping(value = "/refresh", method = RequestMethod.GET)
    public void refresh(){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        //设置content_type为json要不然会报415的错误ff
        httpHeaders.add(HttpHeaders.CONTENT_TYPE,"application/json");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(null,httpHeaders);
        //以post方法访问真正的刷新链接
        ResponseEntity<String> stringResponseEntity = restTemplate.postForEntity("http://localhost:8861/actuator/bus-refresh",
                request, String.class);
    }
}
