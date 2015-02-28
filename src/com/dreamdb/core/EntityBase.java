package com.dreamdb.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.dreamdb.core.interfaces.EntityDAO;
import com.dreamdb.entity.Column;
import com.dreamdb.util.EntityUtil;
import com.dreamdb.util.EnumUtil;
import com.dreamdb.util.XMLUtil;

public abstract class EntityBase implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	private EntityDAO entityDAO;

	public EntityBase() {
		if (XMLUtil.getInstance().getDBType()
				.equals(EnumUtil.DBType.MYSQL.toString())) {// mysql
			entityDAO = DAOImpSingleton.getMySQLImplement();
		} else if (XMLUtil.getInstance().getDBType()
				.equals(EnumUtil.DBType.MSSQL.toString())) {// mssql
			// TODO 坐等莫哥完成mssql
		} else if (XMLUtil.getInstance().getDBType()
				.equals(EnumUtil.DBType.ORACLE.toString())) {// oracle
		}
	}

	protected void autoCreateTable() {
		entityDAO.autoCreateTable(this);
	}

	public <T> boolean save() {
		try {
			entityDAO.save(this);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean delete() {
		if (this.getId() == null || this.getId() < 0) {
			System.out.println("delete方法需要传入ID");
			return false;
		}
		try {
			entityDAO.deleteById(this, this.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@SuppressWarnings("unchecked")
	public <T> T load() {
		if (this.getId() == null || this.getId() < 0) {
			System.out.println("load方法需要传入ID");
			return null;
		}
		return (T) entityDAO.findById(this.getClass(), this.getId());
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> findListBySQL(String conditionSQL, Object... params) {
		return (List<T>) entityDAO.findListBySQL(this.getClass(), conditionSQL,
				params);
	}

	public boolean update() {
		try {
			entityDAO.update(this);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public String toString() {
		EntityUtil entityUtil = new EntityUtil(this);
		Map<String, Column> columus = entityUtil.getColumns();
		Collection<Column> c = columus.values();
		Iterator<Column> it = c.iterator();
		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()) {
			Column column = it.next();
			buffer.append(column.getName()).append(":")
					.append(column.getValue()).append(", ");
		}
		return buffer.toString();
	}

	public void print() {
		EntityUtil entityUtil = new EntityUtil(this);
		Map<String, Column> columus = entityUtil.getColumns();
		Collection<Column> c = columus.values();
		Iterator<Column> it = c.iterator();
		StringBuffer buffer = new StringBuffer();
		while (it.hasNext()) {
			Column column = it.next();
			buffer.append(column.getName()).append(":")
					.append(column.getValue()).append(", ");
		}
		System.out.println("【print】:" + buffer.toString());
	}

}
