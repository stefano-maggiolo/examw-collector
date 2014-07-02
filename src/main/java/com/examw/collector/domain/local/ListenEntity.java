package com.examw.collector.domain.local;

import java.io.Serializable;

/**
 * 
 * @author fengwei.
 * @since 2014年6月28日 下午5:10:41.
 */
public class ListenEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,updateDate,address;
	private Integer orderNum;
	private GradeEntity grade;
	/**
	 * 获取序号。
	 * @return 序号。
	 */
	public Integer getOrderNum() {
		return orderNum;
	}
	/**
	 * 设置序号。
	 * @param num
	 * 序号。
	 */
	public void setOrderNum(Integer num) {
		this.orderNum = num;
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
	/**
	 * 获取 所属班级
	 * @return grade
	 * 所属班级
	 */
	public GradeEntity getGrade() {
		return grade;
	}
	/**
	 * 设置 所属班级
	 * @param grade
	 * 所属班级
	 */
	public void setGrade(GradeEntity grade) {
		this.grade = grade;
	}
	/**
	 * 获取 ID
	 * @return id
	 * ID
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 ID
	 * @param id
	 * ID
	 */
	public void setId(String id) {
		this.id = id;
	}
	
}
