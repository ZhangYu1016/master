package com.parse;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.annotation.RequestParam;
import com.context.ContextLoader;
import com.context.ExceptionConstant;
import com.exception.AppExcption;
import com.handler.HandlerAdapter;
import com.sevlet.DispatcherServlet;
import com.util.BeanUtil;

public class ParseXml {

	private static Logger log = Logger.getLogger(ParseXml.class.getClass());
	
	private static Document document = null;
	private static SAXReader reader = null;
	private static File file = null;
	static {
		
		if(reader == null) {
			reader = new SAXReader();
		}
	}
	
	public synchronized static void parseXml(String dirPath) {
		try {
			BeanUtil.setServletConfig(BeanUtil.getServletConfig());
			file = new File(dirPath);
			document = reader.read(file);
			Element element = document.getRootElement();
			// 通过element对象的elementIterator方法获取迭代器
            @SuppressWarnings("rawtypes")
			Iterator it = element.elementIterator();
            // 遍历迭代器，获取根节点中的信息
            while (it.hasNext()) {
            	Element book = (Element) it.next();
            	//判断是否使用注解编程
            	if(ContextLoader.COMPONENT_SCAN.equals(book.getName())) {
            		if(book.attributeValue(ContextLoader.CONFIG_BASE_PACKAGE) != null) {
            			String packageValue = book.attributeValue(ContextLoader.CONFIG_BASE_PACKAGE).replaceAll(" ", "");;
            			if(packageValue.endsWith(".*")) {
            				packageValue = BeanUtil.getServletConfig().getServletContext().getRealPath("/")+
            						ContextLoader.CLASS_PATH_FOLDER+
            						packageValue.substring(0, packageValue.length() - ".*".length());
            			}
            			String realPath = BeanUtil.getServletConfig().getServletContext().getRealPath("/");
            			realPath = realPath.substring(0, realPath.length()-1);
            			initBeans(packageValue, realPath);
            		}
            	}else {
            		//不使用注解
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(reader != null) {
				reader = null;
			}
			if(document != null) {
				document = null;
			}
			if(file !=null) {
				file = null;
			}
		}
	}
	//获取类名称
	public synchronized static void initBeans(String dir, String realPath) {
		
		try {
			file = new File(dir);
			boolean flag = file.exists();
			if(flag) {
				File[] files = file.listFiles();
				if(files.length>0) {
					for(File f : files) {
						if(f.isDirectory()) {
							initBeans(f.getAbsolutePath(), realPath);
						}else {
							String className = f.getName();
							className = className.substring(0, className.length() -
									ContextLoader.CLASS_SUFFIX.length());
//								className = f.getParent()+"\\"+className;
							className = (f.getPath()).substring((realPath+ContextLoader.CLASS_PATH_FOLDER).length());
							className = className.substring(0,className.length() - ContextLoader.CLASS_SUFFIX.length());
							className = className.replaceAll("\\\\", ".");
							if(!className.contains(ContextLoader.ADAPTERS_PACKAGE)) {
								setHandlerMapping(className);
							}else if(className.contains(ContextLoader.ADAPTERS_PACKAGE)){
								setHandlerAdapter(className);
							}
						}
					}
				}else {
					log.info(file.getName()+"文件夹为空");
				}
			}else {
				log.info("文件夹不存在");
			}
		} catch (Exception e) {
			//抛出单例异常
			throw new AppExcption(ExceptionConstant.CONTR_EXCPTION, e);
		}finally {
			if(file != null) {
				file = null;
			}
		}
		
	}
	
	//判断类上面是否有Controller与RequestMapping注解,如果有则创建对象并放到BaseHandlerMapping对象
	public synchronized static <T> void setHandlerAdapter(String className) {
		try {
			className = className.replaceAll("\\\\", ".");
			@SuppressWarnings("unchecked")
			String classPath = BeanUtil.getServletConfig().getServletContext().getRealPath("/");
			Class<T> t = (Class<T>) Class.forName(className);
			if(!t.isInterface()) {
				HandlerAdapter adapter = (HandlerAdapter) t.newInstance();
				DispatcherServlet.adapters.add(adapter);
				log.info("成功添加适配器对象成功:");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//判断类上面是否有Controller与RequestMapping注解,如果有则创建对象并放到BaseHandlerMapping对象
	public synchronized static <T> void setHandlerMapping(String className) {
		Object o = null;
		boolean isController = false;
		String controllerValue = "";
		String controllerMappingValue = "";
		try {
			@SuppressWarnings("unchecked")
			Class<T> t = (Class<T>) Class.forName(className);
			if(!t.isAnnotation()) {
				Controller a = t.getAnnotation(Controller.class);
				if(a != null) {
					Annotation[] as = t.getAnnotations();
					for(Annotation ab : as) {
						if(ab instanceof Controller) {
							controllerValue = ((Controller) ab).value();
							if(controllerValue.equals("")) {
								controllerValue = lowerFirdt(t.getSimpleName());
								log.info("注解上没有赋值则使用类名首字母小写:"+controllerValue);
							}
							isController = true;
						}
						if(ab instanceof RequestMapping) {
							controllerMappingValue = ((RequestMapping) ab).value();
						}
					}
					//此判断为单例对象设计
					if(isController) {
						//判断类是否已存在
						Object controllerObj = ContextLoader.OBJ_MAP.get(controllerValue);
						if(controllerObj == null) {
							log.info(controllerValue);
							o = t.newInstance();
							//存储
							ContextLoader.OBJ_MAP.put(controllerValue, o);
						}else {
							//抛出单例异常
							throw new AppExcption(ExceptionConstant.CONTR_EXCPTION);
						}
					}
				}
				if(!controllerValue.equals("")) {
					//进行方法映射设置
					setMethodRequestMapping(className, controllerMappingValue, o);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
		
	//判断方法上面是否有RequestMapping注解,如果有则存到方法Map对象中
	public synchronized static <T> Object setMethodRequestMapping(String className, String controllerMappingValue, Object o) {
		try {
			className = className.replaceAll("\\\\", ".");
			@SuppressWarnings("unchecked")
			Class<T> t = (Class<T>) Class.forName(className);
			Method[] methods = t.getDeclaredMethods();
			for(Method method : methods) {
				boolean isRequstAnnotaion = method.isAnnotationPresent(RequestMapping.class);
				if(isRequstAnnotaion) {
					RequestMapping methodMapping = method.getAnnotation(RequestMapping.class);
					//controller上的mapping与方法上的mapping拼接
					String controllerMappingAndMehodMapping = controllerMappingValue+methodMapping.value();
					//存方法
					ContextLoader.METHOD_OBJ_MAP.put(controllerMappingAndMehodMapping, method);
					ContextLoader.METHOD_OF_OBJ_MAP.put(controllerMappingAndMehodMapping, o);
					int index = 0;
					//获取参数注解
					Parameter[] paramMehod = method.getParameters();
					if(paramMehod.length > 0 && paramMehod != null) {
						for(Parameter param : paramMehod) {
							RequestParam requestParam = param.getAnnotation(RequestParam.class);
							//参数位置
							ContextLoader.FILED_SPLACE_MAP.put(requestParam.value(), index++);
							//把参数位置map赋值到方法参数map中,方便后续请求找到方法及参数
							ContextLoader.METHOD_FILED_MAP.put(controllerMappingAndMehodMapping, ContextLoader.FILED_SPLACE_MAP);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new AppExcption(ExceptionConstant.SETMETHOD_MAPPING_EXCPTION, e);
		}
		return o;
	}
	//首字母小写
	public static String lowerFirdt(String str) {
		
		char[] c = str.toCharArray();
		c[0]+=32;
		return String.valueOf(c);
	}
	
	public synchronized static <T> void setMethodRequestParams(String className, String controllerKey) {
		
	}
	
	public static void main(String[] args) {
		
		log.info("com.handler.Test.class".contains("com.handler"));
	}
}
