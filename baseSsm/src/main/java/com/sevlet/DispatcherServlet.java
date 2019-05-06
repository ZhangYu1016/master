package com.sevlet;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.context.ContextLoader;
import com.handler.AnnotationHandlerAdapter;
import com.handler.HandlerAdapter;
import com.handler.SimpleControllerhandlerAdapter;

public class DispatcherServlet extends FrameworkServlet{

	private static Logger log = Logger.getLogger("lavasoft");
	
	public static List<HandlerAdapter> adapters = new ArrayList<HandlerAdapter>();

	@Override
	protected void onRefresh() {
		initStrategies();
	}
	protected void initStrategies() {
		initHandlerAdapters();
	}
	
	public void initHandlerMappings() {
		
	}
	
	public void initViewResolvers() {
		
	}
	
	public void initHandlerAdapters() {
		adapters.add(new AnnotationHandlerAdapter());
		adapters.add(new SimpleControllerhandlerAdapter());
	}
	//��ȡ������
	public HandlerAdapter getHandler(Object controller){  
		
        for(HandlerAdapter adapter: this.adapters){  
            if(adapter.supports(controller)){  
                return adapter;  
            }  
        }  
        return null;  
    }  
	protected void doDispatch(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		//��һ����ȡ�����ַ
		String methodUrl = req.getRequestURI().intern();
		//��ȡ��Ŀ����
		String contextPath = req.getContextPath();
		//����·��ȥ����Ŀ���Ƽ�����ԡ�.����β�ĺ�׺�ϳ����󷽷�key
		methodUrl = methodUrl.substring(contextPath.length(),methodUrl.lastIndexOf("."));
		log.info("�����ַ"+methodUrl);
		req.setAttribute("methodUrl", methodUrl);
		//�ڶ�����ȡ�������
		@SuppressWarnings("rawtypes")
		Enumeration parameter = req.getParameterNames();
		
		Object o = ContextLoader.METHOD_OF_OBJ_MAP.get(methodUrl);
		Map<String, Integer> FILED_SPLACE_MAP = ContextLoader.METHOD_FILED_MAP.get(methodUrl);
		String[] ss = null;
		if(FILED_SPLACE_MAP != null) {
			ss = new String[FILED_SPLACE_MAP.size()];
			if(parameter!=null) {
				while(parameter.hasMoreElements()) {
					String sss = (String) parameter.nextElement();
					log.info("�������:"+sss);
					for(Entry<String, Integer> s : FILED_SPLACE_MAP.entrySet()) {
						if(s.getKey().equals(sss)) {
							ss[s.getValue()] = req.getParameter(s.getKey());
						}
					}
				}
			}else {
				
			}
		}
		
		HandlerAdapter h = getHandler(o);
		h.handle(o, ss);
	}

	@Override
	protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//������
		
		//ִ�з���
		doDispatch(request, response);
	}
	
	public static synchronized int getAdaptersSize() {
		return adapters.size();
	}
}
