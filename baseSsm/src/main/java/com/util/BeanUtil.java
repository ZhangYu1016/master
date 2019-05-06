package com.util;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.context.ContextLoader;

public class BeanUtil {

	public static ServletConfig servletConfig = null;
	
	public static HttpServletRequest httpServletRequest = null;
	
	public static HttpSession httpSession = null;
	
	
	public static void setServletConfig(ServletConfig servletConfig) {
		BeanUtil.servletConfig = servletConfig;
	}
	public static ServletConfig getServletConfig() {
		
		return servletConfig;
	}
	public static void setHttpServletRequest(HttpServletRequest httpServletRequest) {
		BeanUtil.httpServletRequest = httpServletRequest;
	}
	public static HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}
	
	public static HttpSession getHttpSession() {
		return httpServletRequest.getSession();
	}
	
	public Object getBean(String key) {
		if(key!=null && !key.equals("")) {
			return ContextLoader.OBJ_MAP.get(key);
		}
		return null;
	}
}
