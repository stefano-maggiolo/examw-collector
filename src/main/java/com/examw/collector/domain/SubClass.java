package com.examw.collector.domain;

import java.io.Serializable;
/**
 * 科目班级。
 * @author yangyong.
 * @since 2014-06-27.
 */
public class SubClass implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name,teacherName,demo,start,end;
	private Boolean isLive,isShow;
	private Integer longDay,sourcePrice,salePrice,total;
	private AdVideo adVideo;
	private Subject subject;
	/**
	 * 获取所属科目。
	 * @return 所属科目。
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置所属科目。
	 * @param subject
	 * 所属科目。
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
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
	 * 获取广告视频。
	 * @return 广告视频。
	 */
	public AdVideo getAdVideo() {
		return adVideo;
	}
	/**
	 * 设置广告视频集合。
	 * @param adVideo
	 * 广告视频集合。
	 */
	public void setAdVideo(AdVideo adVideo) {
		this.adVideo = adVideo;
	}
}