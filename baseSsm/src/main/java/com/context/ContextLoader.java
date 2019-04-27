package com.context;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextLoader {

	//springMvc位置
	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
	//扫描的包
	public static final String CONFIG_BASE_PACKAGE = "base-package";
	//自动扫描包的标签
	public static final String COMPONENT_SCAN = "component-scan";
	//自动扫描包的标签
	public static final String ANNOTATION_DRIVER = "annotation-driven";
	public static final String CLASS_PATH_PREFIX = "classpath:";
	public static final String CLASS_PATH_FOLDER = "\\WEB-INF\\classes\\";
	public static final String CLASS_SUFFIX = ".class";
	//方法参数Param类型
	public static final String REQUESTPARAM = "RequestParam";
	//类对象，用来判断对象是否已经存在
	public static final Map<String, Object> OBJ_MAP = new ConcurrentHashMap<String, Object>();
	//方法所属类MAP
	public static final Map<String, Object> METHOD_OF_OBJ_MAP = new ConcurrentHashMap<String, Object>();
	//方法对应的请求路径
	public static final Map<String, Method> METHOD_OBJ_MAP = new ConcurrentHashMap<String, Method>();
	//方法参数类型
	public static final Map<String, Map<String, Integer>> METHOD_FILED_MAP = new ConcurrentHashMap<String, Map<String, Integer>>();
	//方法参数位置
	public static final Map<String, Integer> FILED_SPLACE_MAP = new ConcurrentHashMap<String, Integer>();
	//方法返回类型
	public static final Map<String, String> METHOD_RETURN_MAP = new ConcurrentHashMap<String, String>();
}
