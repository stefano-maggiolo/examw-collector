package com.examw.collector.domain.local;

import java.io.Serializable;

/**
 * 
 * @author fengwei.
 * @since 2014年6月28日 下午5:08:35.
 */
public class GradeEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,teacherName,start,end,teacherId;
	private Integer longDay,sourcePrice,salePrice,total;
	private SubjectEntity subjectEntity;
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public SubjectEntity getSubjectEntity() {
		return subjectEntity;
	}
	/**
	 * 设置所属科目。
	 * @param subject
	 * 所属科目。
	 */
	public void setSubjectEntity(SubjectEntity subject) {
		this.subjectEntity = subject;
	}
	/**
	 * 获取班级代码。
	 * @return 班级代码。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置班级代码。
	 * @param code
	 * 班级代码。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取班级名称。
	 * @return 班级名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置班级名称。
	 * @param name
	 * 班级名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取老师名称。
	 * @return 老师名称。
	 */
	public String getTeacherName() {
		return teacherName;
	}
	/**
	 * 设置老师名称。
	 * @param teacherName
	 * 老师名称。
	 */
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	/**
	 * 获取 老师ID
	 * @return teacherId
	 * 
	 */
	public String getTeacherId() {
		return teacherId;
	}
	/**
	 * 设置  老师ID
	 * @param teacherId
	 * 
	 */
	public void setTeacherId(String teacherId) {
		this.teacherId = teacherId;
	}
	/**
	 * 获取班级的原售价。
	 * @return 班级的原售价。
	 */
	public Integer getSourcePrice() {
		return sourcePrice;
	}
	/**
	 * 设置班级的原售价。
	 * @param sourcePrice
	 * 班级的原售价。
	 */
	public void setSourcePrice(Integer sourcePrice) {
		this.sourcePrice = sourcePrice;
	}
	/**
	 * 获取班级销售价格。
	 * @return 班级销售价格。
	 */
	public Integer getSalePrice() {
		return salePrice;
	}
	/**
	 * 设置班级销售价格。
	 * @param salePrice
	 * 班级销售价格。
	 */
	public void setSalePrice(Integer salePrice) {
		this.salePrice = salePrice;
	}
	/**
	 * 获取总讲数。
	 * @return 总讲数。
	 */
	public Integer getTotal() {
		return total;
	}
	/**
	 * 设置总讲数。
	 * @param total
	 * 总讲数。
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
	/**
	 * 获取开课日期。
	 * @return 开课日期。
	 */
	public String getStart() {
		return start;
	}
	/**
	 * 设置开课日期。
	 * @param start
	 * 开课日期。
	 */
	public void setStart(String start) {
		this.start = start;
	}
	/**
	 * 获取接课日期。
	 * @return 接课日期。
	 */
	public String getEnd() {
		return end;
	}
	/**
	 * 设置接课日期。
	 * @param end
	 * 接课日期。
	 */
	public void setEnd(String end) {
		this.end = end;
	}
	/**
	 * 获取 天数
	 * @return longDay
	 * 天数
	 */
	public Integer getLongDay() {
		return longDay;
	}
	/**
	 * 设置 天数
	 * @param longDay
	 * 天数
	 */
	public void setLongDay(Integer longDay) {
		this.longDay = longDay;
	}
	
	
}