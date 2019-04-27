package com.controller;

import com.annotation.Controller;
import com.annotation.RequestMapping;

@Controller("testController2")
@RequestMapping("/test2")
public class TestController2 {

	@RequestMapping("test2")
	public void test() {
		
		System.out.println("test2----------------");
	}
	
}
