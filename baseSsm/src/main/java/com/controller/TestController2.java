package com.controller;

import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.annotation.RequestParam;

@Controller
@RequestMapping("/test2")
public class TestController2 {

	@RequestMapping("/test2")
	public void test(@RequestParam(value = "param") String param) {
		
		System.out.println("test2----------------"+param);
	}
	
}
