package com.examw.collector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 定时器的更新记录
 * @author fengwei.
 * @since 2014年7月28日 上午10:15:14.
 */
public class UpdateLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private Integer type;
	private Date updateTime;
	
	public final static int TYPE_UPDATE_SUBJECT = 1,TYPE_UPDATE_GRADE=2,TYPE_UPDATE_PACK=3;
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
	 * 获取  类型
	 * @return type
	 * 
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 类型
	 * @param type
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取  更新时间
	 * @return updateTime
	 * 
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置 更新时间
	 * @param updateTime
	 * 
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 构造方法
	 * @param id
	 * @param name
	 * @param type
	 * @param updateTime
	 */
	public UpdateLog(String id, String name, Integer type, Date updateTime) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.updateTime = updateTime;
	}
	public UpdateLog(){}
}
