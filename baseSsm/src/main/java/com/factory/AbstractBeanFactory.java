package com.factory;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractBeanFactory implements BeanFactory {

	
	private static final Map<String, Object> beans = new HashMap<String, Object>();
	
	
	public <T> T getBean(String name) {
		return (T) beans.get(name);
	}

	public boolean containsBean(String name) {
		return false;
	}
	
	
}
