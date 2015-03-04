package com.easydb.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.easydb.entity.Column;

/**
 * 实体类操作工具
 * @author kiang-mac
 *
 */
public class EntityUtil {
	private String tableName;
	private Object o;
	private Map<String,Column> columus=new HashMap<String, Column>();
	
	/**
	 * 构造方法，传入一个已经new了的类
	 *<br>该方法执行后，tableName会被负值
	 * 
	 * @param o new了的类
	 */
	public  EntityUtil(Object o){
		this.o=o;
		tableName=o.getClass().getSimpleName();
	}
	

	/**
	 * 
	 * 检测是否基本数据类型
	 * @param clasz
	 * @return
	 */
	private boolean checkTypeIsBaseDataType(Class<?> clasz) {
		if (clasz.getName().equals("java.lang.String") || clasz == String.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Long") || clasz == Long.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Double") || clasz == Double.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Float") || clasz == Float.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Boolean") || clasz == Boolean.class) {
			return true;
		}
		if (clasz.getName().equals("java.lang.Integer")
				|| clasz == Integer.class) {
			return true;
		}
		if (clasz.getName().equals("int") || clasz == int.class) {
			return true;
		}
		if (clasz.getName().equals("double") || clasz == double.class) {
			return true;
		}
		if (clasz.getName().equals("float") || clasz == float.class) {
			return true;
		}
		if (clasz.getName().equals("boolean") || clasz == boolean.class) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * 根据类名获取数据库中的列明和类型和值，
	 * <br>当然，create表的时候，获取不到对应列的值
	 * @return
	 */
	public Map<String, Column> getColumns(){
		
		Field[] f = o.getClass().getDeclaredFields();
		for (Field field : f) {
			if (!field.getName().equals("serialVersionUID")
					&& this.checkTypeIsBaseDataType(field.getType())) {
				Method method;
				try {
					method = o.getClass().getMethod(this.getMethodName(EnumUtil.Method.get, field.getName()));
					Object value=method.invoke(o);
					Column column=new Column();
					column.setName(field.getName());
					
					column.setType(this.getDBColumnType(field.getType()));
					if(column.getName().equals("id")){
						System.out.println(column.getType());
					}
					column.setValue(value);
					columus.put(field.getName(), column);
				} catch (SecurityException e) {
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}

				
			}
		}
		return columus;
	}
	/**
	 * 反射调获取get/set方法
	 * 
	 * @param method
	 *            EnumUtil.Method.get/set
	 * @param attribute
	 * @return
	 */
	public String getMethodName(EnumUtil.Method method, String attribute) {
		String firstElemntOfAttribute = attribute.substring(0, 1).toUpperCase();
		String restElementOfAttrute = attribute.substring(1);
		return method.name() + firstElemntOfAttribute + restElementOfAttrute;
	}
	
	public String getTableName() {
		return tableName;
	}
	
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	/**
	 * 
	 * 根据类的名字获取数据库的标准列明
	 * @param clasz
	 * @return
	 */
	public String getDBColumnType(Class<?> clasz){
		if (clasz.getName().equals("java.lang.String") || clasz == String.class) {
			return "varchar(32)";
		}
		if (clasz.getName().equals("java.lang.Long") || clasz == Long.class) {
			return "int";
		}
		if (clasz.getName().equals("java.lang.Double") || clasz == Double.class) {
			return "double";
		}
		if (clasz.getName().equals("java.lang.Float") || clasz == Float.class) {
			return "float";
		}
		if (clasz.getName().equals("java.lang.Boolean") || clasz == Boolean.class) {
			return "int";
		}
		if (clasz.getName().equals("java.lang.Integer")
				|| clasz == Integer.class) {
			return "int";
		}
		if (clasz.getName().equals("int") || clasz == int.class) {
			return "int";
		}
		if (clasz.getName().equals("double") || clasz == double.class) {
			return "double";
		}
		if (clasz.getName().equals("float") || clasz == float.class) {
			return "float";
		}
		if (clasz.getName().equals("boolean") || clasz == boolean.class) {
			return "int";
		}
		return null;
		
	}
}
