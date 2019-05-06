package com.sevlet;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.context.ContextLoader;
import com.parse.ParseXml;
import com.util.BeanUtil;

public abstract class FrameworkServlet extends HttpServlet{

	private static Logger log = Logger.getLogger("lavasoft");
	
	
	private String contextConfigLocation;
	
	public void setContextConfigLocation(String contextConfigLocation) {
		this.contextConfigLocation = contextConfigLocation;
	}
	
	public String getContextConfigLocation() {
		return contextConfigLocation;
	}
	
	
	protected abstract void doService(HttpServletRequest request, HttpServletResponse response)
			throws Exception;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		BeanUtil.setServletConfig(config);
		log.info("start Initialization springMvc...");
		this.contextConfigLocation = config.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM);
		this.contextConfigLocation = this.contextConfigLocation.substring(ContextLoader.CLASS_PATH_PREFIX.length());
		this.contextConfigLocation = config.getServletContext().getRealPath("/")+
				ContextLoader.CLASS_PATH_FOLDER+
				contextConfigLocation;
		ParseXml.parseXml(contextConfigLocation);
		//初始化
		onRefresh();
		log.info("springMvc Initialization successful");
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}
	
	protected final void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			
			//添加一些拦截器
			//执行方法
			doService(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void onRefresh() {
		// For subclasses: do nothing by default.
	}
	
	protected com.support.WebApplicationContext initWebApplicationContext() {
		return null;
	}
}
