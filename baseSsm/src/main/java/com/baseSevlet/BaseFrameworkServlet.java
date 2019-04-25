package com.baseSevlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.context.BaseContextLoader;
import com.context.BaseExceptionConstant;
import com.exception.AppExcption;
import com.parse.BaseParseXml;

public abstract class BaseFrameworkServlet extends HttpServlet{

	private static Logger logger = Logger.getLogger(BaseFrameworkServlet.class);
	
	
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
		this.contextConfigLocation = config.getInitParameter(BaseContextLoader.CONFIG_LOCATION_PARAM);
		this.contextConfigLocation = this.contextConfigLocation.substring(BaseContextLoader.CLASS_PATH_PREFIX.length());
		this.contextConfigLocation = config.getServletContext().getRealPath("/")+
				BaseContextLoader.CLASS_PATH_FOLDER+
				contextConfigLocation;
		BaseParseXml.parseXml(contextConfigLocation, config);
		System.out.println("获得的 contextConfigLocation:"+this.contextConfigLocation+"\n加载完毕 contextConfigLocation");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Method m = BaseContextLoader.CONTROLLER_MAP.get("testController_/test").get("testController_/test/test22");
		if(m!=null) {
			Class c = BaseContextLoader.CLASS_MAP.get("testController_/test");
			Object o;
			try {
				o = c.newInstance();
				m.invoke(o);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}else {
			throw new AppExcption(BaseExceptionConstant.REQUST_EXCPTION);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}
	
	
	
}
