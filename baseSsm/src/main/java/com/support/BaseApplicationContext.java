package com.support;

import com.factory.BaseAbstractBeanFactory;

public interface BaseApplicationContext {

	void refresh();
	
	public abstract BaseAbstractBeanFactory getBeanFactory();
}
