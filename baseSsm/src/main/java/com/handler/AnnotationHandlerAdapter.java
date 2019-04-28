package com.handler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.annotation.Controller;
import com.context.ContextLoader;
import com.sevlet.DispatcherServlet;
import com.util.BeanUtil;

public class AnnotationHandlerAdapter implements HandlerAdapter {

	public boolean supports(Object handler) {
		
		if(handler.getClass().isAnnotationPresent(Controller.class)) {
			return true;
		}
		return false;
	}

	public void handle(Object handler) {
		Map<String, Object> map = ContextLoader.METHOD_OF_OBJ_MAP;
		Set<Entry<String, Object>> set = map.entrySet();
		String methodKey = null;
		for(Entry<String, Object> entry : set) {
			if(entry.getValue() == handler) {
				methodKey = entry.getKey();
			}
		}
		Method m = ContextLoader.METHOD_OBJ_MAP.get(methodKey);
		try {
			m.invoke(handler, "ce1", "ce2");
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
	}

	public String getName() {
		return this.getName();
	}
}
