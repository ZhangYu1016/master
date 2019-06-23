package com.demo.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
@FeignClient(value = "MovieConfigClient1" )
public interface FeignService {

    @RequestMapping("/hi")
    String getTest();
}
