package com.examw.collector.model;

import java.io.Serializable;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.model.Paging;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午11:14:19.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SubjectInfo  extends Paging implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name,status;
	private Integer classTotal;
	private String catalogId,catalogName;
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
	 * 获取 科目名称
	 * @return name
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 科目名称
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 课程总数
	 * @return classTotal
	 * 
	 */
	public Integer getClassTotal() {
		return classTotal;
	}
	/**
	 * 设置 课程总数
	 * @param classTotal
	 * 
	 */
	public void setClassTotal(Integer classTotal) {
		this.classTotal = classTotal;
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
	 * 获取 类别名称
	 * @return catalogName
	 * 
	 */
	public String getCatalogName() {
		return catalogName;
	}
	/**
	 * 设置 类别名称
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
	
}
