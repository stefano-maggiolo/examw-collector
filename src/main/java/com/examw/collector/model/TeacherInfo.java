package com.examw.collector.model;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.collector.support.CustomDateSerializer;
import com.examw.model.Paging;

/**
 * 
 * @author fengwei.
 * @since 2014年7月9日 上午10:51:36.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TeacherInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name,description,lessons,info,imgurl,education,schoolName;
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
	@JsonSerialize(using=CustomDateSerializer.class)
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
	
	public String subjectId,catalogId;
	
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
	 * 获取 科目分类ID
	 * @return catalogId
	 * 
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置 科目分类ID
	 * @param catalogId
	 * 
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	
}
