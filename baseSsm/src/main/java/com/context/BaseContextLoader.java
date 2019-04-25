package com.context;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseContextLoader {

	//springMvc位置
	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
	//扫描的包
	public static final String CONFIG_BASE_PACKAGE = "base-package";
	//自动扫描包的标签
	public static final String COMPONENT_SCAN = "component-scan";
	public static final String CLASS_PATH_PREFIX = "classpath:";
	public static final String CLASS_PATH_FOLDER = "\\WEB-INF\\classes\\";
	public static final String CLASS_SUFFIX = ".class";
	//类对应的
	public static final Map<String, Map<String, Method>> CONTROLLER_MAP = new ConcurrentHashMap<String, Map<String, Method>>();
	//装在类对象
	public static final Map<String, Class> CLASS_MAP = new ConcurrentHashMap<String, Class>();
	//方法对应的请求路径
	public static final Map<String, Method> METHOD_MAP = new ConcurrentHashMap<String, Method>();
	//方法参数类型
	public static final Map<String, String> METHOD_Filed_MAP = new ConcurrentHashMap<String, String>();
	//方法返回类型
	public static final Map<String, String> METHOD_RETURN_MAP = new ConcurrentHashMap<String, String>();
}
