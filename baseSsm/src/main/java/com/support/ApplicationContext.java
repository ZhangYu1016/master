package com.support;

import com.factory.AbstractBeanFactory;

public interface ApplicationContext {

	void refresh();
	
	public abstract AbstractBeanFactory getBeanFactory();
}
