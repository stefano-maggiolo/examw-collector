package com.examw.collector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 错误记录数据[页面没有ID,插入不进]
 * @author fengwei.
 * @since 2014年7月28日 上午10:02:55.
 */
public class ErrorRecord implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id, dataId, name , note, status ,classId;
	private Integer type;
	private Date updateTime;
	public static final int TYPE_ERROR_GRADE = 2,TYPE_ERROR_PACK = 3;
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
	 * 获取 数据ID
	 * @return dataId
	 * 
	 */
	public String getDataId() {
		return dataId;
	}
	/**
	 * 设置 数据ID
	 * @param dataId
	 * 
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
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
	 * 获取 备注
	 * @return note
	 * 
	 */
	public String getNote() {
		return note;
	}
	/**
	 * 设置 备注
	 * @param note
	 * 
	 */
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 获取 类型
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
	 * 获取  状态
	 * @return status
	 * 
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * 设置 状态
	 * @param status
	 * 
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * 获取 更新时间
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
	 * 获取 分类ID[非环球]
	 * @return classId
	 * 
	 */
	public String getClassId() {
		return classId;
	}
	/**
	 * 设置 分类ID
	 * @param classId
	 * 
	 */
	public void setClassId(String classId) {
		this.classId = classId;
	}
	
	public ErrorRecord() {
	}
	public ErrorRecord(String id, String dataId, String name, String note,
			Integer type, String status, Date updateTime,String classId) {
		super();
		this.id = id;
		this.dataId = dataId;
		this.name = name;
		this.note = note;
		this.type = type;
		this.status = status;
		this.updateTime = updateTime;
		this.classId = classId;
	}
	
}
