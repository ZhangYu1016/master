package com.factory;

public class PropertyValues {

	private final String name;

	private final Object value;
	
	public PropertyValues(String name, Object value) {
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
