package com.factory;

public interface BaseBeanFactory {

	<T> T getBean(String name, Class<T> requiredType);
	
	boolean containsBean(String name);
}
