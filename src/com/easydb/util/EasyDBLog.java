package com.easydb.util;


public class EasyDBLog {
	
	
	
	public static void alert(String msg){
		StringBuilder builder=new StringBuilder("【");
		builder.append(msg);
		builder.append("】");
		System.out.println(builder);
	}
	
	public static void error(String msg){
		StringBuilder builder=new StringBuilder("【");
		builder.append(msg);
		builder.append("】");
		System.out.println(builder);
	}
	
	/**
	 * sql执行语句，根据用户配置文件来决定是否显示SQL语句
	 * @param msg
	 */
	public static void sql(String msg){
		if(XMLUtil.getInstance().getPrintSQL().equals("true")){
			StringBuilder builder=new StringBuilder("【sql语句】:\n");
			builder.append(msg);
			builder.append("");
			System.out.println(builder);
		}
	}
	
	public static void debug(String msg){
		//TODO 建议做个级别判断
		/**
		 * if(outLevel==1){
		 * 	//error 语句写入日志
		 * }else if(outLevel==2){
		 *  //debug 语句写入日志
		 * }
		 * */
		
		//TODO 大概这样，我不怎么清楚如何实现，可以考虑写成单例或者通过配置文件加载
	}
	
	public static void info(){
		
	}
	
}
