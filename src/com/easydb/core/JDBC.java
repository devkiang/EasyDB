package com.easydb.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.easydb.util.DreamDBLog;
import com.easydb.util.XMLUtil;

public class JDBC {
	private static Connection conn;
	
	public static Connection getConnetion(){
	if(conn==null){
				try {
					XMLUtil util=XMLUtil.getInstance();
					util.xmlName="Dream";
					util.parseCoreXML();
				String url=util.getDBURL()+util.getDBName()+"?characterEncoding=UTF-8";
				String dbname=util.getDBAccount();
				String dbpassword=util.getDBPassword();
				if(dbpassword==null){
					dbpassword="";
				}
				if(util.getDBType().equals("MYSQL")){
					Class.forName("com.mysql.jdbc.Driver");
				}else if(util.getDBType().equals("MSSQL")){
					//其他数据库
				}else{
					DreamDBLog.error("错误的数据库类型配置:"+util.getDBType());
				}
				conn = DriverManager.getConnection(url, dbname, dbpassword);
				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		return conn;
	}

//	public static void main(String[] args) {
//		String sql="CREATE TABLE if not exists BigDog2 (good int NOT NULL  ,PRIMARY KEY  (good) );";
//		try {
//			Statement stmt = JDBC.getConnetion().createStatement() ;   
//		       PreparedStatement pstmt = conn.prepareStatement(sql) ;   
//        pstmt.executeUpdate();
//			System.out.println("ok");
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
	
//	public void close(){
//		try {
//			if(!conn.isClosed()){
//				conn.close();
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
}
