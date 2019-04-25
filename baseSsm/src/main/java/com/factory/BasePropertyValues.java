package com.factory;

public class BasePropertyValues {

	private final String name;

	private final Object value;
	
	public BasePropertyValues(String name, Object value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public Object getValue() {
		return value;
	}
}
