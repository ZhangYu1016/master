package com.handler;

public interface HandlerAdapter {

	public boolean supports(Object handler);  
    public void handle(Object handler);  
    public String getName();
}
