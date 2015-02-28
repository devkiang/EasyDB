package com.dreamdb.util;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;




public class XMLUtil {
	
	/**
	 * xml名，因为使用了单例，所以只能设置一次，建议在ContextLoaderListener启动时调用
	 */
	public String xmlName;
	private Element root;
	private static XMLUtil xmlUtil;
	
	/**
	 * 单例读取配置文件
	 * @return
	 */
	public static XMLUtil getInstance(){
		
		if(xmlUtil==null){
			xmlUtil=new XMLUtil();
		}
		return xmlUtil;
	}
	
	
	/**
	 * 开始解析XML文件，该方法建议让web.xml（ContextLoaderLostemter）来调用一次即可
	 */
	public void parseCoreXML(){
		SAXBuilder builder = new SAXBuilder();
		if(xmlName==null||xmlName.length()<1){
			DreamDBLog.error("没有读取到配置文件");
			return;
		}
		try {
			Document doc = builder.build(new File(this.getClass().getClassLoader()
					.getResource("").getPath()+ xmlName+".xml"));
			root = doc.getRootElement(); // 根元素 dogs
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * 读取xml配置中的数据库路径
	 * @return
	 */
	public String getDBURL(){
		Element dbNode=root.getChild("DB");
		return this.getNodeValue(dbNode,"URL");
	}
	
	/**
	 * 读取数据库账号
	 * @return
	 */
	public String getDBAccount(){
		return this.getDBNodeValue("Account");
	}
	
	/**
	 * 读取数据库密码
	 * @return
	 */
	public String getDBPassword(){
		return this.getDBNodeValue("Password");
	}
	
	/**
	 * 读取数据库类型
	 * @return
	 */
	public String getDBType(){
		return this.getDBNodeValue("DBType");
	}
	
	/**
	 * 读取是否自动建表
	 * @return
	 */
	public String getCreateTable(){
		return this.getDBNodeValue("CreateTable");
	}
	
	/**
	 * 读取是否打印sql语句
	 * @return
	 */
	public String getPrintSQL(){
		return this.getDBNodeValue("PrintSQL");
	}
	
	/**
	 * 读取实体类目录
	 * @return
	 */
	public String getEntityPath(){
		return this.getDBNodeValue("EntityPath");
	}
	
	/**
	 * 读取数据库名
	 * @return
	 */
	public String getDBName(){
		return this.getDBNodeValue("DBName");
	}
	
	
	
	/**
	 * 获取制定元素下的节点值
	 * @param element 元素，比如《test》《name》拿到的值《/name》《/test》
	 * @param nodeName 节点名：比如上面的name
	 * @return 返回值，比如上面的“拿到的值”
	 */
	public String getNodeValue(Element element,String nodeName){
		if(element.getChild(nodeName)==null){
			return null;
		}
		return element.getChild(nodeName).getValue();
	}
	
	/**
	 * 获取DB元素下对应的节点值
	 * @param nodeName 节点名
	 * @return
	 */
	public String getDBNodeValue(String nodeName){
		Element dbNode=root.getChild("DB");
		if(dbNode.getChild(nodeName)==null){
			return null;
		}
		return dbNode.getChild(nodeName).getValue();
	}

}
