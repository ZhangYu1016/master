package com.context;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ContextLoader {

	//springMvcλ��
	public static final String CONFIG_LOCATION_PARAM = "contextConfigLocation";
	//ɨ��İ�
	public static final String CONFIG_BASE_PACKAGE = "base-package";
	//�Զ�ɨ����ı�ǩ
	public static final String COMPONENT_SCAN = "component-scan";
	//�Զ�ɨ����ı�ǩ
	public static final String ANNOTATION_DRIVER = "annotation-driven";
	public static final String CLASS_PATH_PREFIX = "classpath:";
	public static final String CLASS_PATH_FOLDER = "\\WEB-INF\\classes\\";
	public static final String CLASS_SUFFIX = ".class";
	//��������Param����
	public static final String REQUESTPARAM = "RequestParam";
	//����������ж϶����Ƿ��Ѿ�����
	public static final Map<String, Object> OBJ_MAP = new ConcurrentHashMap<String, Object>();
	//����������MAP
	public static final Map<String, Object> METHOD_OF_OBJ_MAP = new ConcurrentHashMap<String, Object>();
	//������Ӧ������·��
	public static final Map<String, Method> METHOD_OBJ_MAP = new ConcurrentHashMap<String, Method>();
	//������������
	public static final Map<String, Map<String, Integer>> METHOD_FILED_MAP = new ConcurrentHashMap<String, Map<String, Integer>>();
	//��������λ��
	public static final Map<String, Integer> FILED_SPLACE_MAP = new ConcurrentHashMap<String, Integer>();
	//������������
	public static final Map<String, String> METHOD_RETURN_MAP = new ConcurrentHashMap<String, String>();
}
