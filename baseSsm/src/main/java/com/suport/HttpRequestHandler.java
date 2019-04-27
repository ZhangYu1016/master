package com.suport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface HttpRequestHandler {

	public void handleRequest(HttpServletRequest request, 
			HttpServletResponse response) throws Exception;
}
