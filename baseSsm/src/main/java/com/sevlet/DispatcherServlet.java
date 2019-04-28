package com.sevlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.annotation.Controller;
import com.context.ContextLoader;
import com.handler.AnnotationHandlerAdapter;
import com.handler.HandlerAdapter;
import com.handler.SimpleControllerhandlerAdapter;
import com.util.BeanUtil;

public class DispatcherServlet extends FrameworkServlet{

	private static Logger log = Logger.getLogger(DispatcherServlet.class.getClass());
	
	public static final List<HandlerAdapter> adapters = new ArrayList<HandlerAdapter>();

	public void initStrategies() {
		
	}
	
	public void initHandlerMappings() {
		
	}
	
	public void initViewResolvers() {
		
	}
	
	public void initHandlerAdapters() {
		//通过反射注入所有的适配器
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
	protected void doDispatch(String methodUrl) throws Exception {
		Object o = ContextLoader.METHOD_OF_OBJ_MAP.get(methodUrl);
		HandlerAdapter h = getHandler(o);
		h.handle(o);
	}
}
