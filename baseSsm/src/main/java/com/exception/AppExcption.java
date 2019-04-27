package com.exception;

public class AppExcption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public AppExcption(String msg) {
		super(msg);
	}
	
	public AppExcption(String msg, Throwable o) {
		super(msg, o);
	}
}
