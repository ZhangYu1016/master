package com.parse;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletConfig;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

import com.annotation.Controller;
import com.annotation.RequestMapping;
import com.context.BaseContextLoader;
import com.context.BaseExceptionConstant;
import com.exception.AppExcption;

public class BaseParseXml {

	private static Document document = null;
	private static SAXReader reader = null;
	static {
		
		if(reader == null) {
			reader = new SAXReader();
		}
	}
	
	public static void parseXml(String dirPath, ServletConfig config) {
		try {
			File dirFile = new File(dirPath);
			document = reader.read(dirFile);
			Element element = document.getRootElement();
			// 通过element对象的elementIterator方法获取迭代器
            Iterator it = element.elementIterator();
            // 遍历迭代器，获取根节点中的信息（书籍）
            while (it.hasNext()) {
            	Element book = (Element) it.next();
            	if(BaseContextLoader.COMPONENT_SCAN.equals(book.getName())) {
            		if(book.attributeValue(BaseContextLoader.CONFIG_BASE_PACKAGE) != null) {
            			String packageValue = book.attributeValue(BaseContextLoader.CONFIG_BASE_PACKAGE).replaceAll(" ", "");;
            			if(packageValue.endsWith(".*")) {
            				packageValue = config.getServletContext().getRealPath("/")+
            						BaseContextLoader.CLASS_PATH_FOLDER+
            						packageValue.substring(0, packageValue.length() - ".*".length());
            			}
            			String realPath = config.getServletContext().getRealPath("/");
            			realPath = realPath.substring(0, realPath.length()-1);
            			initBeans(packageValue, realPath);
            		}
            	}
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//获取类名称
		public static void initBeans(String dir, String realPath) {
			
			File file = new File(dir);
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
									BaseContextLoader.CLASS_SUFFIX.length());
//							className = f.getParent()+"\\"+className;
							className = (f.getPath()).substring((realPath+BaseContextLoader.CLASS_PATH_FOLDER).length());
							className = className.substring(0,className.length() - BaseContextLoader.CLASS_SUFFIX.length());
							setHandlerMapping(className);
						}
					}
				}else {
					System.out.println(file.getName()+"文件夹为空");
				}
			}else {
				System.out.println("文件夹不存在");
			}
			
		}
		
		//判断类上面是否有Controller与RequestMapping注解,如果有则创建对象并放到BaseHandlerMapping对象
		public synchronized static <T> Object setHandlerMapping(String className) {
			Object o = null;
			boolean isController = false;
			String controllerValue = "";
			String controllerMappingValue = "";
			try {
				className = className.replaceAll("\\\\", ".");
				Class<T> t = (Class<T>) Class.forName(className);
				if(!t.isAnnotation()) {
					Controller a = t.getAnnotation(Controller.class);
					if(a != null) {
						Annotation[] as = t.getAnnotations();
						for(Annotation ab : as) {
							if(ab instanceof Controller) {
								controllerValue = ((Controller) ab).value();
								isController = true;
							}
							if(ab instanceof RequestMapping) {
								controllerMappingValue = ((RequestMapping) ab).value();
							}
						}
						
						if(isController) {
							Map controllerValueFlag = BaseContextLoader.CONTROLLER_MAP.get(controllerValue);
							if(controllerValueFlag == null || controllerValueFlag.size()==0) {
								System.out.println(controllerValue);
								BaseContextLoader.CONTROLLER_MAP.put(controllerValue+"_"+controllerMappingValue, BaseContextLoader.METHOD_MAP);
							}else {
								throw new AppExcption(BaseExceptionConstant.CONTR_EXCPTION);
							}
						}
					}
					if(!controllerValue.equals("")) {
						setMethodRequestMapping(className, controllerValue+"_"+controllerMappingValue);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return o;
		}
	
		
	//判断类上面是否有RequestMapping注解,如果有则存到方法Map对象中
	public synchronized static <T> Object setMethodRequestMapping(String className, String controllerKey) {
		Object o = null;
		try {
			className = className.replaceAll("\\\\", ".");
			@SuppressWarnings("unchecked")
			Class<T> t = (Class<T>) Class.forName(className);
			Method[] methods = t.getDeclaredMethods();
			for(Method method : methods) {
				boolean isRequstAnnotaion = method.isAnnotationPresent(RequestMapping.class);
				if(isRequstAnnotaion) {
					RequestMapping reqValue = method.getAnnotation(RequestMapping.class);
					BaseContextLoader.METHOD_MAP.put(controllerKey+reqValue.value(), method);
					Parameter[] p = method.getParameters();
					for(Parameter p2 : p) {
						System.out.println("方法参数:"+p2.getName());
					}
				}
			}
			int methodRequestSize = BaseContextLoader.METHOD_MAP.size();
			if(methodRequestSize>0 && BaseContextLoader.METHOD_MAP != null) {
				BaseContextLoader.CONTROLLER_MAP.put(controllerKey, BaseContextLoader.METHOD_MAP);
				BaseContextLoader.CLASS_MAP.put(controllerKey, t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return o;
	}
	
	
	public static void main(String[] args) {
		String dir = "C:\\computer\\git\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\baseSsm\\WEB-INF\\classes\\com";
		String realPath = "C:\\computer\\git\\workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\baseSsm";
		String packageValue = "com. *".replaceAll(" ", "");
		System.out.println(packageValue+"\n"+packageValue.endsWith(".*"));
	}
}
