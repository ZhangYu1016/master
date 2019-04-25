package com.controller;

import com.annotation.Controller;
import com.annotation.RequestMapping;

@Controller("testController")
@RequestMapping("/test")
public class TestController {

	@RequestMapping("/test2")
	public void test(String requst) {
		
		System.out.println("test2----------------");
	}
	
}
