package com.controller;

import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.annotation.RequestParam;

@Controller("testController")
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/test2")
	public void test(@RequestParam(value = "param") String param, @RequestParam(value = "param2")String param2) {
		
		System.out.println(param+"----------------"+param2);
	}
	
}
