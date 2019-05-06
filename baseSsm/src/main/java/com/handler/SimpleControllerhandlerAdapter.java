package com.handler;

import com.annotation.Controller;
import com.suport.ModelAndView;

public class SimpleControllerhandlerAdapter implements HandlerAdapter {

	public boolean supports(Object handler) {
		if(handler instanceof Controller) {
			return true;
		}
		return false;
	}

	public ModelAndView handle(Object handler, String[] s) {
		
		//Ö´ÐÐ·½·¨
		return null;
	}
}
