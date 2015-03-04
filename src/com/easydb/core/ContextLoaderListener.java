package com.easydb.core;

import java.io.File;

import com.easydb.util.EasyDBLog;
import com.easydb.util.XMLUtil;


/**
 * 【该类用于非Servlet平台】
 * 
 * @author kiang-mac
 *
 */
public class ContextLoaderListener{

	//创建建表语句
	/**
	 * 初始化，创建表、读取XML配置文件、初始化JDBC（通过建表语句来初始化JDBC，所以启动的时候略慢）
	 */
	public void init(){
		XMLUtil util=XMLUtil.getInstance();
		util.xmlName="Easy";//配置文件名
		util.parseCoreXML();//解析xml文件
		String configPath=XMLUtil.getInstance().getEntityPath().replace(".", "/");//获取实体类目录
		String entityPath="src/"+configPath;
		String entityPathDot=configPath.replace("/",".");
		File file = new File(entityPath); 
		File[] files=file.listFiles();//读取目录下所有文件
		for (File subFile : files) {
			int dot=subFile.getName().lastIndexOf(".");
        	String hz=subFile.getName().substring(dot,subFile.getName().length());//后缀名
        	String name=subFile.getName().substring(0,dot);//类名
        		try {
        			if(hz.equals(".class")||hz.equals(".java")){//后缀名是class的将实例化
        				EntityBase o=(EntityBase) Class.forName(entityPathDot+"."+name).newInstance();
        				o.autoCreateTable();//自动建表语句
        				System.out.println(o.getClass()+" 加载完毕");
        			}
				} catch (InstantiationException e) {
					EasyDBLog.error("目录中包含错误的实体类");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (java.lang.ClassCastException e) {
					EasyDBLog.error("目录中包含非法的实体类:"+entityPathDot+"."+name+",Dream实体类必须继承自EntityBase类");
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
	
	public ContextLoaderListener(){
			this.init();
	}
}
