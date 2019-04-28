package com.sevlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import com.context.ContextLoader;
import com.parse.ParseXml;
import com.util.BeanUtil;

public abstract class FrameworkServlet extends HttpServlet{

	private static Logger log = Logger.getLogger(FrameworkServlet.class.getClass());
	
	
	private String contextConfigLocation;
	
	public void setContextConfigLocation(String contextConfigLocation) {
		this.contextConfigLocation = contextConfigLocation;
	}
	
	public String getContextConfigLocation() {
		return contextConfigLocation;
	}
	protected void doDispatch(String methodUrl) throws Exception{}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		BeanUtil.setServletConfig(config);
		log.info(config.getServletName());
		log.info("��ʼ���� contextConfigLocation");
		this.contextConfigLocation = config.getInitParameter(ContextLoader.CONFIG_LOCATION_PARAM);
		this.contextConfigLocation = this.contextConfigLocation.substring(ContextLoader.CLASS_PATH_PREFIX.length());
		this.contextConfigLocation = config.getServletContext().getRealPath("/")+
				ContextLoader.CLASS_PATH_FOLDER+
				contextConfigLocation;
		ParseXml.parseXml(contextConfigLocation);
		log.info("��õ� contextConfigLocation:"+this.contextConfigLocation+"\n������� contextConfigLocation");
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//��һ����ȡ�����ַ
		//��һ����ȡ�����ַ
		String methodUrl = req.getRequestURI().intern();
		//��ȡ��Ŀ����
		String contextPath = req.getContextPath();
		//����·��ȥ����Ŀ���Ƽ�����ԡ�.����β�ĺ�׺�ϳ����󷽷�key
		methodUrl = methodUrl.substring(contextPath.length(),methodUrl.lastIndexOf("."));
		log.info("�����ַ"+methodUrl);
		//�ڶ�����ȡ�������
		@SuppressWarnings("rawtypes")
		Enumeration parameter = req.getParameterNames();
		if(parameter!=null) {
			while(parameter.hasMoreElements()) {
				log.info("�������:"+parameter.nextElement());
			}
		}else {
			
		}
		try {
			doDispatch(methodUrl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//�ӷ���map��ȡ������
//		Method m = ContextLoader.METHOD_OBJ_MAP.get(methodUrl);
//		//�ж��Ƿ���ڴ�·���򷽷�
//		if(m!=null) {
//			//ͨ������key�Ӷ���MAP�л�ȡִ�з����Ķ����жϴ�����Ķ����Ƿ�Ϊ��
//			Object o = ContextLoader.METHOD_OF_OBJ_MAP.get(methodUrl);
//			log.info("ִ����ĸ��ࣺ:");
//			if(o!=null) {
//				try {
//					String[] s = new String[] {};
//					Map<String, Integer> FILED_SPLACE_MAP = ContextLoader.METHOD_FILED_MAP.get(methodUrl);
//					
//					Set<Entry<String, Integer>> set = FILED_SPLACE_MAP.entrySet();
//					
//					for(Entry<String, Integer> e : set) {
//						log.info("key:"+e.getKey()+"\r\nvalue"+e.getValue());
////						if() {
////							if(e.getKey().equals(anObject)) {
////								
////							}
////						}
//					}
//					//ִ�з���
//					m.invoke(o, "1", "2");
//				} catch (Exception e) {
//					e.printStackTrace();
//				} 
//			}else {
//				throw new AppExcption(ExceptionConstant.REQUST_OBJ_EXCPTION);
//			}
//		}else {
//			throw new AppExcption(ExceptionConstant.REQUST_EXCPTION);
//		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}
