package com.examw.collector.domain.local;

import java.io.Serializable;
import java.util.Date;

/**
 * 老师数据
 * @author fengwei.
 * @since 2014年7月8日 下午5:34:00.
 */
public class TeacherEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,lessons,info,imgurl,education,schoolName,catalogId;
	private Date addDate;
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
	public String getDescription() {
		return description;
	}
	/**
	 * 设置 描述
	 * @param discription
	 * 
	 */
	public void setDescription(String description) {
		this.description = description;
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
	 * 获取 信息
	 * @return info
	 * 
	 */
	public String getInfo() {
		return info;
	}
	/**
	 * 设置信息
	 * @param info
	 * 
	 */
	public void setInfo(String info) {
		this.info = info;
	}
	/**
	 * 获取 图片地址
	 * @return imgurl
	 * 
	 */
	public String getImgurl() {
		return imgurl;
	}
	/**
	 * 设置 图片地址
	 * @param imgurl
	 * 
	 */
	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}
	/**
	 * 获取 学历
	 * @return education
	 * 
	 */
	public String getEducation() {
		return education;
	}
	/**
	 * 设置 学历
	 * @param education
	 * 
	 */
	public void setEducation(String education) {
		this.education = education;
	}
	/**
	 * 获取 学校名字
	 * @return schoolName
	 * 
	 */
	public String getSchoolName() {
		return schoolName;
	}
	/**
	 * 设置 学校名字
	 * @param schoolName
	 * 
	 */
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	/**
	 * 获取 创建时间
	 * @return addDate
	 * 
	 */
	public Date getAddDate() {
		return addDate;
	}
	/**
	 * 设置 创建时间
	 * @param addDate
	 * 
	 */
	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	/**
	 * 获取 所受课程分类ID(小类)
	 * @return catalogId
	 * 
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置 所受课程分类ID
	 * @param catalogId
	 * 
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		if(id!=null)
		{
			buf.append(id).append(":").append(id.length()).append("; ");
		}
		if(name!=null)
		{
			buf.append(name).append(":").append(name.length()).append("; ");
		}
		if(lessons!=null)
		{
			buf.append(lessons).append(":").append(lessons.length()).append("; ");
		}
		if(catalogId!=null)
		{
			buf.append(catalogId).append(":").append(catalogId.length()).append("; ");
		}
		if(imgurl!=null)
		{
			buf.append(imgurl).append(":").append(imgurl.length()).append("; ");
		}
		return buf.toString();
	}
}
