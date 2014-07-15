package com.examw.collector.model;

import java.io.Serializable;

import com.examw.model.Paging;

/**
 * 套餐信息
 * @author fengwei.
 * @since 2014年6月30日 上午11:18:16.
 */
public class PackInfo extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name,status;
	private String classCodes;
	private Integer source,discount;
	private boolean isShow;
	private String catalogId,catalogName,subjectId,subjectName;
	/**
	 * 获取 编码
	 * @return code
	 * 
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置 编码
	 * @param code
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取 包含课程ID
	 * @return classCodes
	 * 
	 */
	public String getClassCodes() {
		return classCodes;
	}
	/**
	 * 设置 包含课程ID
	 * @param classCodes
	 * 
	 */
	public void setClassCodes(String classCodes) {
		this.classCodes = classCodes;
	}
	/**
	 * 获取 原价
	 * @return source
	 * 
	 */
	public Integer getSource() {
		return source;
	}
	/**
	 * 设置 原价
	 * @param source
	 * 
	 */
	public void setSource(Integer source) {
		this.source = source;
	}
	/**
	 * 获取  优惠价
	 * @return discount
	 * 
	 */
	public Integer getDiscount() {
		return discount;
	}
	/**
	 * 设置 优惠价
	 * @param discount
	 * 
	 */
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	/**
	 * 获取 是否显示
	 * @return isShow
	 * 
	 */
	public boolean getIsShow() {
		return isShow;
	}
	/**
	 * 设置 是否显示
	 * @param isShow
	 * 
	 */
	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取 类别ID
	 * @return catalogId
	 * 
	 */
	public String getCatalogId() {
		return catalogId;
	}
	/**
	 * 设置 类别ID
	 * @param catalogId
	 * 
	 */
	public void setCatalogId(String catalogId) {
		this.catalogId = catalogId;
	}
	/**
	 * 获取 类别名字
	 * @return catalogName
	 * 
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置 类别名字
	 * @param catalogName
	 * 
	 */
	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
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
	 * 获取 科目名字
	 * @return subjectName
	 * 
	 */
	public String getSubjectName() {
		return subjectName;
	}
	/**
	 * 设置 科目名字
	 * @param subjectName
	 * 
	 */
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
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
