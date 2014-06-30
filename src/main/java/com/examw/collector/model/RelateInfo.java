package com.examw.collector.model;

import java.io.Serializable;

import com.examw.model.Paging;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午11:22:51.
 */
public class RelateInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,update,address;
	private Integer num;
	private boolean isDemo,isNew;
	private String classId,className;
	
	/**
	 * 获取 ID
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 名称
	 * @return name
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 名称
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 更新时间
	 * @return update
	 * 
	 */
	public String getUpdate() {
		return update;
	}
	/**
	 * 设置 更新时间
	 * @param update
	 * 
	 */
	public void setUpdate(String update) {
		this.update = update;
	}
	/**
	 * 获取 试听地址
	 * @return address
	 * 
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置 试听地址
	 * @param address
	 * 
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * 获取 序号
	 * @return num
	 * 
	 */
	public Integer getNum() {
		return num;
	}
	/**
	 * 设置 序号
	 * @param num
	 * 
	 */
	public void setNum(Integer num) {
		this.num = num;
	}
	/**
	 * 获取 是否试听
	 * @return isDemo
	 * 
	 */
	public boolean isDemo() {
		return isDemo;
	}
	/**
	 * 设置 是否试听
	 * @param isDemo
	 * 
	 */
	public void setDemo(boolean isDemo) {
		this.isDemo = isDemo;
	}
	/**
	 * 获取 是否新的
	 * @return isNew
	 * 
	 */
	public boolean isNew() {
		return isNew;
	}
	/**
	 * 设置 是否新的
	 * @param isNew
	 * 
	 */
	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}
	/**
	 * 获取  班级ID
	 * @return classId
	 * 
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置 班级ID
	 * @param classId
	 * 
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	/**
	 * 获取 班级名称
	 * @return className
	 * 
	 */
	public String getClassName() {
		return className;
	}
	/**
	 * 设置 班级名称
	 * @param className
	 * 
	 */
	public void setClassName(String className) {
		this.className = className;
	}
	
}
