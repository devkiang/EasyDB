package test.model;

import com.easydb.core.EntityBase;

public class BigPerson extends EntityBase{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8211603916356703237L;
	private String name;
	private String address;
	private boolean sex;
	private Integer age;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public boolean getSex() {
		return sex;
	}
	public void setSex(boolean sex) {
		this.sex = sex;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
}
