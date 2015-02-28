package com.dreamdb.core.interfaces;

import java.sql.SQLException;
import java.util.List;

/**
 * 
 * 数据库操作接口
 * @author kiang-mac
 *
 */
public interface EntityDAO {
	/**
	 * @param entity
	 */
	public void save(Object entity) throws SQLException;
	/**
	 * 保存数据
	 * @param entity
	 */
	public void update(Object entity)throws SQLException;
	/**
	 * 更新数据
	 * @param id
	 */
	public void deleteById(Object entity,Integer id)throws SQLException;
	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 */
	public Object findById(Class<?> clasz,Integer id);
	/**
	 * 根据sql语句来查询结果集
	 * @param sql
	 * @return
	 */
	public List<?> findListBySQL(Class<?> clasz,String conditionSQL,Object... params);
	/**
	 * 自动建表 如何防止用户使用框架时失误调用？？
	 * @param entity
	 */
	public void autoCreateTable(Object entity);
}

