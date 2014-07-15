package com.examw.collector.model;

import java.io.Serializable;

import com.examw.model.Paging;

/**
 * 班级信息
 * @author fengwei.
 * @since 2014年6月30日 上午11:28:52.
 */
public class SubClassInfo  extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name,teacherName,demo,start,end,status;
	private Boolean isLive,isShow;
	private Integer longDay,sourcePrice,salePrice,total;
	private String adVideoId,adVideoName;
	private String subjectId,subjectName,catalogId,catalogName;
	/**
	 * 获取班级代码。
	 * @return 班级代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置班级代码。
	 * @param code
	 * 班级代码。
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取试听讲的集合（多个以半角逗号分割）。
	 * @return 试听讲的集合（多个以半角逗号分割）。
	 */
	public String getDemo() {
		return demo;
	}
	/**
	 * 设置试听讲的集合（多个以半角逗号分割）。
	 * @param demo
	 * 试听讲的集合（多个以半角逗号分割）。
	 */
	public void setDemo(String demo) {
		this.demo = demo;
	}
	/**
	 * 获取是否直播。
	 * @return 是否直播。
	 */
	public Boolean getIsLive() {
		return isLive;
	}
	/**
	 * 设置是否直播。
	 * @param isLive
	 * 是否直播。
	 */
	public void setIsLive(Boolean isLive) {
		this.isLive = isLive;
	}
	/**
	 * 获取是否显示。
	 * @return 是否显示。
	 */
	public Boolean getIsShow() {
		return isShow;
	}
	/**
	 * 设置是否显示。
	 * @param isShow
	 * 是否显示。
	 */
	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取课程时长。
	 * @return 课程时长。
	 */
	public Integer getLongDay() {
		return longDay;
	}
	/**
	 * 设置课程时长。
	 * @param longDay
	 * 课程时长。
	 */
	public void setLongDay(Integer longDay) {
		this.longDay = longDay;
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
	 * 获取 广告ID
	 * @return adVideoId
	 * 
	 */
	public String getAdVideoId() {
		return adVideoId;
	}
	/**
	 * 设置 广告ID
	 * @param adVideoId
	 * 
	 */
	public void setAdVideoId(String adVideoId) {
		this.adVideoId = adVideoId;
	}
	/**
	 * 获取 广告名字
	 * @return adVideoName
	 * 
	 */
	public String getAdVideoName() {
		return adVideoName;
	}
	/**
	 * 设置 广告名字
	 * @param adVideoName
	 * 
	 */
	public void setAdVideoName(String adVideoName) {
		this.adVideoName = adVideoName;
	}
	/**
	 * 获取 科目ID
	 * @return subjectId
	 * 
	 */
	public String getSubjectId() {
		return subjectId;
	}
	/**
	 * 设置 科目ID
	 * @param subjectId
	 * 
	 */
	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	/**
	 * 获取 科目名称
	 * @return subjectName
	 * 
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置 科目名称
	 * @param subjectName
	 * 
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	/**
	 * 获取 分类ID
	 * @return catalogId
	 * 
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置  分类ID
	 * @param catalogId
	 * 
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取 分类名称 
	 * @return catalogName
	 * 
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置 分类名称
	 * @param catalogName
	 * 
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
	}
	/**
	 * 获取 状态
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
	
	private String updateInfo;
	public String getUpdateInfo() {
		return updateInfo;
	}
	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}
	
}
