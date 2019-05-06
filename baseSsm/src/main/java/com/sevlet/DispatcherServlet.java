package com.sevlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.context.ContextLoader;
import com.handler.AnnotationHandlerAdapter;
import com.handler.HandlerAdapter;
import com.handler.SimpleControllerhandlerAdapter;

public class DispatcherServlet extends FrameworkServlet{

	private static Logger log = Logger.getLogger("lavasoft");
	
	public static List<HandlerAdapter> adapters = new ArrayList<HandlerAdapter>();

	@Override
	protected void onRefresh() {
		initStrategies();
	}
	protected void initStrategies() {
		initHandlerAdapters();
	}
	
	public void initHandlerMappings() {
		
	}
	
	public void initViewResolvers() {
		
	}
	
	public void initHandlerAdapters() {
		adapters.add(new AnnotationHandlerAdapter());
		adapters.add(new SimpleControllerhandlerAdapter());
	}
	//获取适配器
	public HandlerAdapter getHandler(Object controller){  
		
        for(HandlerAdapter adapter: this.adapters){  
            if(adapter.supports(controller)){  
                return adapter;  
            }  
        }  
        return null;  
    }  
	protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//第一步获取请求地址
		String methodUrl = req.getRequestURI().intern();
		//获取项目名称
		String contextPath = req.getContextPath();
		//请求路径去除项目名称及最后以“.”结尾的后缀合成请求方法key
		methodUrl = methodUrl.substring(contextPath.length(),methodUrl.lastIndexOf("."));
		log.info("请求地址"+methodUrl);
		req.setAttribute("methodUrl", methodUrl);
		//第二部获取请求参数
		@SuppressWarnings("rawtypes")
		Enumeration parameter = req.getParameterNames();
		
		Object o = ContextLoader.METHOD_OF_OBJ_MAP.get(methodUrl);
		Map<String, Integer> FILED_SPLACE_MAP = ContextLoader.METHOD_FILED_MAP.get(methodUrl);
		String[] ss = null;
		if(FILED_SPLACE_MAP != null) {
			ss = new String[FILED_SPLACE_MAP.size()];
			if(parameter!=null) {
				while(parameter.hasMoreElements()) {
					String sss = (String) parameter.nextElement();
					log.info("请求参数:"+sss);
					for(Entry<String, Integer> s : FILED_SPLACE_MAP.entrySet()) {
						if(s.getKey().equals(sss)) {
							ss[s.getValue()] = req.getParameter(s.getKey());
						}
					}
				}
			}else {
				
			}
		}
		
		HandlerAdapter h = getHandler(o);
		h.handle(o, ss);
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//请求处理
		
		//执行方法
		doDispatch(request, response);
	}
	
	public static synchronized int getAdaptersSize() {
		return adapters.size();
	}
}
