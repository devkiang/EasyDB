package com.dreamdb.util;

public class EnumUtil {
	/**
	 * 基本数据类型
	 * @author kiang-mac
	 *
	 */
	public enum BaseDataTypeName{
		String,Integer,Boolean,Double,Float,Long
		
	};
	
	/**
	 * get和set方法
	 * @author kiang-mac
	 *
	 */
	public enum Method{
		get,set
		
	}
	
	/**
	 * 数据库类型
	 * @author kiang-mac
	 *
	 */
	public enum DBType{
		MYSQL,MSSQL,ORACLE
	}
}
