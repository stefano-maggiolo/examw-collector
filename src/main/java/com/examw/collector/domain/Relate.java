package com.examw.collector.domain;

import java.io.Serializable;

/**
 * 课时（讲）。
 * @author yangyong.
 * @since 2014-06-28.
 */
public class Relate implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name,updateDate,address;
	private Integer num;
	private boolean isDemo,isNew;
	/**
	 * 获取序号。
	 * @return 序号。
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置序号。
	 * @param num
	 * 序号。
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 获取标题。
	 * @return 标题。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置标题。
	 * @param name
	 * 标题。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取是否试听。
	 * @return 是否试听。
	 */
	public boolean getIsDemo() {
		return isDemo;
	}
	/**
	 * 设置是否试听。
	 * @param isDemo
	 * 是否试听。
	 */
	public void setIsDemo(boolean isDemo) {
		this.isDemo = isDemo;
	}
	/**
	 * 获取更新日期。
	 * @return 更新日期。
	 */
	public String getUpdateDate() {
		return updateDate;
	}
	/**
	 * 设置更新日期。
	 * @param update
	 * 更新日期。
	 */
	public void setUpdateDate(String update) {
		this.updateDate = update;
	}
	/**
	 * 获取是否最新课程。
	 * @return 是否最新课程。
	 */
	public boolean getIsNew() {
		return isNew;
	}
	/**
	 * 设置是否最新课程。
	 * @param isNew
	 * 是否最新课程。
	 */
	public void setIsNew(boolean isNew) {
		this.isNew = isNew;
	}
	/**
	 * 获取试听地址。
	 * @return 试听地址。
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置试听地址。
	 * @param address
	 * 试听地址。
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	
	//所在班级
	private SubClass subclass;
	/**
	 * 获取 所在班级
	 * @return subclass
	 * 
	 */
	public SubClass getSubclass() {
		return subclass;
	}
	/**
	 * 设置 所在班级
	 * @param subclass
	 * 
	 */
	public void setSubclass(SubClass subclass) {
		this.subclass = subclass;
	}
	private Integer id;
	/**
	 * 获取 
	 * @return id
	 * 
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * 设置 
	 * @param id
	 * 
	 */
	public void setId(Integer id) {
		this.id = id;
	}
	
}