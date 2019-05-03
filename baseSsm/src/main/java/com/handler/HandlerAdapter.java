package com.handler;

import com.suport.ModelAndView;

public interface HandlerAdapter {

	public boolean supports(Object handler);  
    public ModelAndView handle(Object handler, String[] s);  
}
