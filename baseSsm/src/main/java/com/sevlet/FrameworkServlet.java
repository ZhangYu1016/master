package com.sevlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.context.ContextLoader;
import com.context.ExceptionConstant;
import com.exception.AppExcption;
import com.parse.ParseXml;

public abstract class FrameworkServlet extends HttpServlet{

	private static Logger logger = Logger.getLogger(FrameworkServlet.class);
	
	
	private String contextConfigLocation;
	
	public void setContextConfigLocation(String contextConfigLocation) {
		this.contextConfigLocation = contextConfigLocation;
	}
	
	public String getContextConfigLocation() {
		return contextConfigLocation;
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("开始加载 contextConfigLocation");
		this.contextConfigLocation = config.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM);
		this.contextConfigLocation = this.contextConfigLocation.substring(ContextLoader.CLASS_PATH_PREFIX.length());
		this.contextConfigLocation = config.getServletContext().getRealPath("/")+
				ContextLoader.CLASS_PATH_FOLDER+
				contextConfigLocation;
		ParseXml.parseXml(contextConfigLocation, config);
		System.out.println("获得的 contextConfigLocation:"+this.contextConfigLocation+"\n加载完毕 contextConfigLocation");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		//第一步获取请求地址
		String methodUrl = req.getRequestURI().intern();
		//获取项目名称
		String contextPath = req.getContextPath();
		//请求路径去除项目名称及最后以“.”结尾的后缀合成请求方法key
		methodUrl = methodUrl.substring(contextPath.length(),methodUrl.lastIndexOf("."));
		System.out.println("请求地址"+methodUrl);
		//第二部获取请求参数
		@SuppressWarnings("rawtypes")
		Enumeration parameter = req.getParameterNames();
		if(parameter!=null) {
			while(parameter.hasMoreElements()) {
				System.out.println("请求参数:"+parameter.nextElement());
			}
		}else {
			
		}
		
		
		
		//从方法map中取出方法
		Method m = ContextLoader.METHOD_OBJ_MAP.get(methodUrl);
		//判断是否存在此路径或方法
		if(m!=null) {
			//通过方法key从对象MAP中获取执行方法的对象判断此请求的对象是否为空
			Object o = ContextLoader.METHOD_OF_OBJ_MAP.get(methodUrl);
			System.out.println("执行类的父类：:");
			if(o!=null) {
				try {
					String[] s = new String[] {};
					Map<String, Integer> FILED_SPLACE_MAP = ContextLoader.METHOD_FILED_MAP.get(methodUrl);
					
					Set<Entry<String, Integer>> set = FILED_SPLACE_MAP.entrySet();
					
					for(Entry<String, Integer> e : set) {
						System.out.println("key:"+e.getKey()+"\r\nvalue"+e.getValue());
//						if() {
//							if(e.getKey().equals(anObject)) {
//								
//							}
//						}
					}
					//执行方法
					m.invoke(o, "1", "2");
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}else {
				throw new AppExcption(ExceptionConstant.REQUST_OBJ_EXCPTION);
			}
		}else {
			throw new AppExcption(ExceptionConstant.REQUST_EXCPTION);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	
}
