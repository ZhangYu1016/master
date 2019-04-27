package com.factory;

public interface BeanFactory {

	<T> T getBean(String name, Class<T> requiredType);
	
	boolean containsBean(String name);
}
