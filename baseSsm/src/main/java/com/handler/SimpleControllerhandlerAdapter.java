package com.handler;

import com.annotation.Controller;

public class SimpleControllerhandlerAdapter implements HandlerAdapter {

	public boolean supports(Object handler) {
		if(handler instanceof Controller) {
			return true;
		}
		return false;
	}

	public void handle(Object handler) {
		
		//ִ�з���
	}

	public String getName() {
		return this.getName();
	}

}
