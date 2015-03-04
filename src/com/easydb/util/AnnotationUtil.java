package com.easydb.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AnnotationUtil {
	public static AnnotationUtil shared;
	
	public static AnnotationUtil getShared()
	{
		if(shared==null){
			shared=new AnnotationUtil();
		}
		return shared;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object loadAnnotation(Class annotationClasss,String annotationField,String className) {
        Method[] methods = null;
        Field[] fields=null;
        Object value = null;
		try {
			methods = Class.forName(className).getDeclaredMethods();
			fields =  Class.forName(className).getDeclaredFields();
//        for (Method method : methods) {  
//            if (method.isAnnotationPresent(annotationClasss)) {  
//                Annotation ant = method.getAnnotation(annotationClasss);
//                Method m = ant.getClass().getDeclaredMethod(annotationField, null);
//				value =  m.invoke(ant, null);
//            }  
//        }  
		    for (Field field : fields) {  
	            if (field.isAnnotationPresent(annotationClasss)) {  
	                Annotation ant = field.getAnnotation(annotationClasss);
	                Method m = ant.getClass().getDeclaredMethod(annotationField, null);
					value =  m.invoke(ant, null);
	            }  
	        }  
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassCastException e) {
			e.printStackTrace();
		}
        return value;  
	}
}
