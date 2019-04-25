package com.context;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseContextLoader {

	//springMvcλ��
	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
	//ɨ��İ�
	public static final String CONFIG_BASE_PACKAGE = "base-package";
	//�Զ�ɨ����ı�ǩ
	public static final String COMPONENT_SCAN = "component-scan";
	public static final String CLASS_PATH_PREFIX = "classpath:";
	public static final String CLASS_PATH_FOLDER = "\\WEB-INF\\classes\\";
	public static final String CLASS_SUFFIX = ".class";
	//���Ӧ��
	public static final Map<String, Map<String, Method>> CONTROLLER_MAP = new ConcurrentHashMap<String, Map<String, Method>>();
	//װ�������
	public static final Map<String, Class> CLASS_MAP = new ConcurrentHashMap<String, Class>();
	//������Ӧ������·��
	public static final Map<String, Method> METHOD_MAP = new ConcurrentHashMap<String, Method>();
	//������������
	public static final Map<String, String> METHOD_Filed_MAP = new ConcurrentHashMap<String, String>();
	//������������
	public static final Map<String, String> METHOD_RETURN_MAP = new ConcurrentHashMap<String, String>();
}
