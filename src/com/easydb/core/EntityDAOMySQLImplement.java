package com.easydb.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.easydb.annotation.Id;
import com.easydb.annotation.Id.Type;
import com.easydb.core.interfaces.EntityDAO;
import com.easydb.entity.Column;
import com.easydb.util.AnnotationUtil;
import com.easydb.util.EasyDBLog;
import com.easydb.util.EntityUtil;
import com.easydb.util.EnumUtil.Method;


/**
 * 
 * MYSQL数据库操作实现
 * @author kiang-mac
 *
 */
public class EntityDAOMySQLImplement implements EntityDAO{

	public EntityDAOMySQLImplement(){
		
	}
	
	/** 
	 * 
	 * 保存，自动根据实体类生成建表语句
	 * @throws SQLException 
	 * 
	 */
	@Override
	public void save(Object entity) throws SQLException {
		StringBuilder sql = new StringBuilder("   INSERT INTO ");
		EntityUtil entityUtil=new EntityUtil(entity);
		String tableName=entityUtil.getTableName();
		sql.append(tableName).append("(");
		
		Map<String, Column> columus=entityUtil.getColumns();
		Collection<Column> c = columus.values();
		Iterator<Column> it = c.iterator();
		
		StringBuffer valuesSql=new StringBuffer();
		StringBuffer columnNameSql=new StringBuffer();
		while(it.hasNext()){
			Column column=it.next();
			if(column.getValue()!=null){
				columnNameSql.append(column.getName()+", ");
				if(column.getType().equals("text")||column.getType().equals("varchar(32)")){
					valuesSql.append(" '"+column.getValue()+"', ");
				}else {
					valuesSql.append(" "+column.getValue()+" , ");
				}
			}
		}
		valuesSql=new StringBuffer(valuesSql.substring(0, valuesSql.length()-2));
		columnNameSql=new StringBuffer(columnNameSql.subSequence(0, columnNameSql.length()-2));
		sql.append(columnNameSql).append(") VALUES (").append(valuesSql).append(");");;
		EasyDBLog.sql(sql.toString());
			JDBC.getConnetion().prepareStatement(sql.toString()).executeUpdate();
		
		
	}

	@Override
	public void update(Object entity) throws SQLException {
		EntityUtil entityUtil=new EntityUtil(entity);
		EntityBase entityBase=(EntityBase) entity;
		if(entityBase.getId()==null){
			try {
				throw new EasyDBException("执行update操作必须要传入ID");
			} catch (EasyDBException e) {
				e.printStackTrace();
				return;
			}
		}
		String tableName=entityUtil.getTableName();
		StringBuffer sql=new StringBuffer();
		//UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
		sql.append(" update ").append(tableName).append(" SET ");
		StringBuffer setStr=new StringBuffer();
		Map<String, Column> columus=entityUtil.getColumns();
		Collection<Column> c = columus.values();
		Iterator<Column> it = c.iterator();
		while(it.hasNext()){
			Column column=it.next();
			setStr.append(column.getName()).append(" = ");
			if(!column.getType().equals("text")&&!column.getType().equals("varchar(32)")){
				setStr.append(column.getValue()).append(" ,");
			}else {
				setStr.append("'").append(column.getValue()).append("' ,");
			}
		}
		String set=setStr.substring(0, setStr.lastIndexOf(","));
		
		sql.append(set).append(" where id=").append(entityBase.getId());
			EasyDBLog.sql(sql.toString());
			JDBC.getConnetion().prepareStatement(sql.toString()).executeUpdate();
	}

	@Override
	public void deleteById(Object entity,Object id) throws SQLException {
		EntityUtil entityUtil=new EntityUtil(entity);
		String tableName=entityUtil.getTableName();
		StringBuffer sql=new StringBuffer();
		//UPDATE 表名称 SET 列名称 = 新值 WHERE 列名称 = 某值
		sql.append(" delete from ").append(tableName);
		EntityBase entityBase=(EntityBase) entity;
		sql.append(" where id=").append(entityBase.getId());
			EasyDBLog.sql(sql.toString());
			JDBC.getConnetion().prepareStatement(sql.toString()).executeUpdate();
		
	}

	@Override
	public Object findById(Class<?> clasz,Object id) {
		Object entity = null;
		try {
			entity=clasz.newInstance();
			Object o=clasz.newInstance();
			EntityUtil entityUtil=new EntityUtil(o);
			@SuppressWarnings("unused")
			Map<String,Column> columns=entityUtil.getColumns();
			String tableName=entityUtil.getTableName();
			//select * from tableName where id=2;
			StringBuffer sql=new StringBuffer();
			sql.append("select ");
			StringBuffer columnName=new StringBuffer();
			
			Map<String, Column> columus=entityUtil.getColumns();
			Collection<Column> c = columus.values();
			Iterator<Column> it = c.iterator();
			columnName.append("id,");
			while(it.hasNext()){
				Column column=it.next();
				columnName.append(column.getName()).append(" ,");
			}
			String columnNameStr=columnName.substring(0,columnName.lastIndexOf(",") );
			sql.append(columnNameStr).append(" from ").append(tableName).append(" where id=").append(id);
			EasyDBLog.sql(sql.toString());
			ResultSet rs=JDBC.getConnetion().prepareStatement(sql.toString()).executeQuery();
			Collection<Column> c2 = columus.values();
			Iterator<Column> it2 = c2.iterator();
			while(rs.next()){
				Object idValue=rs.getInt("id");
				String setIDMethod=entityUtil.getMethodName(Method.set, "id");
				Field idField = entity.getClass().getSuperclass().getDeclaredField("id");    
				java.lang.reflect.Method idMethod = entity.getClass().getSuperclass().getMethod(setIDMethod, idField.getType());
				
				
				idMethod.invoke(entity, new Object[] { idValue }); 
				while(it2.hasNext()){
					Column column=it2.next();
					String setMethodName=entityUtil.getMethodName(Method.set,column.getName());
					Object value=rs.getObject(column.getName());
					@SuppressWarnings("rawtypes")
					Class[] parameterTypes = new Class[1];       
				    Field field = entity.getClass().getDeclaredField(column.getName());
				    parameterTypes[0] = field.getType();
				    if(field.getType().equals(boolean.class)){
				    	parameterTypes[0] = boolean.class;
				    	boolean v=false;
				    	if((Integer)value==1){
				    		v=true;
				    	}
						java.lang.reflect.Method method = entity.getClass().getMethod(setMethodName, parameterTypes);
						method.invoke(entity, new Object[] { v }); 
				    }else if(field.getType().equals(Boolean.class)){
				    	parameterTypes[0] = Boolean.class;
				    	Boolean v=false;
				    	if((Integer)value==1){
				    		v=true;
				    	}
						java.lang.reflect.Method method = entity.getClass().getMethod(setMethodName, parameterTypes);
						method.invoke(entity, new Object[] { v }); 
				    }else {
				    	if(setMethodName.equals("setId")){
				    		parameterTypes[0] = Object.class;
				    	}
						java.lang.reflect.Method method = entity.getClass().getMethod(setMethodName, parameterTypes);
						method.invoke(entity, new Object[] { value }); 
				    }
				}
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entity;
	}

	/* (non-Javadoc)
	 * @see com.dreamdb.core.EntityDAO#findListBySQL(java.lang.Class, java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<?> findListBySQL(Class<?> clasz,String conditionSQL,Object... params) {
		List<Object> result=new ArrayList<Object>();
		try {
			
			Object o=clasz.newInstance();//
			EntityUtil entityUtil=new EntityUtil(o);
			@SuppressWarnings("unused")
			Map<String,Column> columns=entityUtil.getColumns();//拿到类中的所有属性
			String tableName=entityUtil.getTableName();//拿到类名
			//select * from tableName where id=2;
			StringBuffer sql=new StringBuffer();//创建SQL语句
			sql.append("select ");//select 语句
			StringBuffer columnName=new StringBuffer();//创建查询的列明
			
			Map<String, Column> columus=entityUtil.getColumns();//读取实体类中的属性来做成列名
			Collection<Column> c = columus.values();//获取到实体类中每个属性的值
			Iterator<Column> it = c.iterator();
			columnName.append("id,");//添加例外的‘id’列名
			while(it.hasNext()){//把属性名添加到列名字符串中
				Column column=it.next();
				columnName.append(column.getName()).append(",");//（id，xx，xxx）
			}
			String columnNameStr=columnName.substring(0,columnName.lastIndexOf(",") );//截取掉最后以为的逗号
			sql.append(columnNameStr).append(" from ").append(tableName).append(" where ").append(conditionSQL);//把用户传来的SQL条件语句添加到SQL语句中
			EasyDBLog.sql(sql.toString());
			
			//--------------------给传来的占位符设置上参数--------------------
			PreparedStatement ps=JDBC.getConnetion().prepareStatement(sql.toString());
			for (int i=0;i<params.length;i++) {
				ps.setObject(i+1, params[i]);//SQL下标从1开始
			}
			
			//-------------------开始执行SQL语句-----------------------------
			ResultSet rs=ps.executeQuery();
			Collection<Column> c2 = columus.values();
			Iterator<Column> it2 = c2.iterator();
			while(rs.next()){
				Object entity=clasz.newInstance();//把传来的Class实例化
				Object idValue=rs.getInt("id");
				String setIDMethod=entityUtil.getMethodName(Method.set, "id");
				Field idField = entity.getClass().getSuperclass().getDeclaredField("id");    
				java.lang.reflect.Method idMethod = entity.getClass().getSuperclass().getMethod(setIDMethod, idField.getType());
				
				idMethod.invoke(entity, new Object[] { idValue }); 
				while(it2.hasNext()){//第二次遍历，值设置到实体类中
					Column column=it2.next();
					String setMethodName=entityUtil.getMethodName(Method.set,column.getName());
					Object value=rs.getObject(column.getName());
					@SuppressWarnings("rawtypes")
					Class[] parameterTypes = new Class[1];       
				    Field field = entity.getClass().getDeclaredField(column.getName());
				    parameterTypes[0] = field.getType();
				    if(field.getType().equals(boolean.class)){
				    	parameterTypes[0] = boolean.class;
				    	boolean v=false;
				    	if((Integer)value==1){
				    		v=true;
				    	}
						java.lang.reflect.Method method = entity.getClass().getMethod(setMethodName, parameterTypes);
						method.invoke(entity, new Object[] { v }); 
				    }else if(field.getType().equals(Boolean.class)){
				    	parameterTypes[0] = Boolean.class;
				    	Boolean v=false;
				    	if((Integer)value==1){
				    		v=true;
				    	}
						java.lang.reflect.Method method = entity.getClass().getMethod(setMethodName, parameterTypes);
						method.invoke(entity, new Object[] { v }); 
				    }else {
				    	if(setMethodName.equals("setId")){
				    		parameterTypes[0] = Object.class;
				    	}
						java.lang.reflect.Method method = entity.getClass().getMethod(setMethodName, parameterTypes);
						method.invoke(entity, new Object[] { value }); 
				    }
				}
				result.add(entity);
			}
			
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.dreamdb.core.EntityDAO#autoCreateTable(java.lang.Object)
	 */
	@Override
	public void autoCreateTable(Object entity) {
		boolean isIdOverride=false;
		StringBuilder sql = new StringBuilder("CREATE TABLE if not exists ");
		EntityUtil entityUtil=new EntityUtil(entity);
		
		//----- 检查表是否存在 ------
		StringBuilder checkTableSql=new StringBuilder("SHOW TABLES LIKE '");//检查是否存在表
		checkTableSql.append(entityUtil.getTableName());
		checkTableSql.append("';");
		boolean tableExists=false;//表格是否存在
		try {
			ResultSet rs=JDBC.getConnetion().prepareStatement(checkTableSql.toString()).executeQuery();
			if(rs.next()){
				tableExists=true;
			}else {
				tableExists=false;
			}
		} catch (SQLException e1) {
			tableExists=false;
			e1.printStackTrace();
		}
		//----- 检查结束  --- - -- --
		
		if(tableExists==true){//如果存在将不再去建表
			return;
		}
		
		Map<String, Column> columns=entityUtil.getColumns();
		String tableName=entityUtil.getTableName();
		sql.append(tableName);
		sql.append("(");

		Collection<Column> c = columns.values();
		Iterator<Column> it = c.iterator();
		while (it.hasNext()) {
			sql.append("\n ");
			Column column = (Column) it.next();
			
			sql.append(column.getName()).append(" ");
			
			if(column.getName().equals(this.getPropertyValueByKey("id"))){//id被重写
				isIdOverride=true;
				String ant =   AnnotationUtil.getShared().loadAnnotation(Id.class, "type", entity.getClass().getName()).toString();
				
//				System.out.println(antMap.size());
//				Iterator i=antMap.keySet().iterator();
//				while(i.hasNext()){
//					String s=(String) i.next();
//					System.out.println("      ________"+s);
//				}
				if (ant.equals("String")) {
					sql.append(" varchar(32) NOT NULL ");
				} else {
					sql.append(" int NOT NULL  AUTO_INCREMENT ");
				}
			}else {
				sql.append(column.getType());
			}
			sql.append(" ,");
		}
		if(isIdOverride==false){//id没有被重写
			System.out.println("重写了");
			sql.append(" \n ");
			sql.append(this.getPropertyValueByKey("id"));
			sql.append(" int NOT NULL  AUTO_INCREMENT  ,");
		}
		
		sql.append("PRIMARY KEY  (");
		sql.append(this.getPropertyValueByKey("id"));
		sql.append(") \n);");
		EasyDBLog.sql(sql.toString());
		try {
			JDBC.getConnetion().prepareStatement(sql.toString()).executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		try {
//			//System.out.println(JDBC.getConnetion().prepareStatement(sql.toString()).executeUpdate());
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 读取xml配置中的ID
	 * 如果xml中不存在《ID》的节点，则返回默认
	 * @param string
	 * @return
	 */
	private Object getPropertyValueByKey(String string) {
//		if(1==1){
//			return "1";
//		}else{
//			//解析XML，那到类型，拿到idNmae--customID
//			return "customID";
//		}
		return "id";
	}
	
	/**
	 * 释放对象，释放内存
	 * @param o
	 */

	
}
