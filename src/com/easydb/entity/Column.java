package com.easydb.entity;

/**
 * 
 * 添加Column实体类，用于存放解析到的实体类数据
 * 
 * @author kiang-mac
 * 
 */
public class Column {
	/**
	 * 数据库行名
	 */
	private String name;
	/**
	 * 数据库类型
	 */
	private String type;
	/**
	 * 行下对应的值
	 */
	private Object value;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
}
