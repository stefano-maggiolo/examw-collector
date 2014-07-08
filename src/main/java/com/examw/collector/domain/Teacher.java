package com.examw.collector.domain;

import java.io.Serializable;

/**
 * 
 * @author fengwei.
 * @since 2014年7月8日 下午5:34:00.
 */
public class Teacher implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,discription,lessons,impression;
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
	 * 获取 名字
	 * @return name
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 名字
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 描述
	 * @return discription
	 * 
	 */
	public String getDiscription() {
		return discription;
	}
	/**
	 * 设置 描述
	 * @param discription
	 * 
	 */
	public void setDiscription(String discription) {
		this.discription = discription;
	}
	/**
	 * 获取 所授课程
	 * @return lessons
	 * 
	 */
	public String getLessons() {
		return lessons;
	}
	/**
	 * 设置 所授课程
	 * @param lessons
	 * 
	 */
	public void setLessons(String lessons) {
		this.lessons = lessons;
	}
	/**
	 * 获取 印象
	 * @return impression
	 * 
	 */
	public String getImpression() {
		return impression;
	}
	/**
	 * 设置 印象
	 * @param impression
	 * 
	 */
	public void setImpression(String impression) {
		this.impression = impression;
	}
	
}
