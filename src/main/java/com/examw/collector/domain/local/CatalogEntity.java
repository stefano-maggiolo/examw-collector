package com.examw.collector.domain.local;

import java.io.Serializable;
import java.util.Set;

/**
 * 课程分类
 * @author fengwei.
 * @since 2014年7月2日 上午9:51:29.
 */
public class CatalogEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,cname,ename;
	private Integer childNum;
	private String schoolId;
	private CatalogEntity parent;
	private Set<CatalogEntity> children;
	public static String SCHOOLID_EDU24 = ",1,2,";	//环球网校的学校代码
	
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
	 * 获取 环球的Code
	 * @return code
	 * 
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置 环球的Code
	 * @param code
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取 中文名
	 * @return cname
	 * 
	 */
	public String getCname() {
		return cname;
	}
	/**
	 * 设置 中文名
	 * @param cname
	 * 
	 */
	public void setCname(String cname) {
		this.cname = cname;
	}
	/**
	 * 获取 英文名
	 * @return ename
	 * 
	 */
	public String getEname() {
		return ename;
	}
	/**
	 * 设置 英文名
	 * @param ename
	 * 
	 */
	public void setEname(String ename) {
		this.ename = ename;
	}
	/**
	 * 获取 子类数量
	 * @return childNum
	 * 
	 */
	public Integer getChildNum() {
		return childNum;
	}
	/**
	 * 设置 子类数量
	 * @param childNum
	 * 
	 */
	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
	}
	/**
	 * 获取 网校ID
	 * @return schoolId
	 * 
	 */
	public String getSchoolId() {
		return schoolId;
	}
	/**
	 * 设置 网校ID
	 * @param schoolId
	 * 
	 */
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	/**
	 * 获取 父类别
	 * @return parent
	 * 
	 */
	public CatalogEntity getParent() {
		return parent;
	}
	/**
	 * 设置 父类别
	 * @param parent
	 * 
	 */
	public void setParent(CatalogEntity parent) {
		this.parent = parent;
	}
	/**
	 * 获取 子类集合
	 * @return children
	 * 
	 */
	public Set<CatalogEntity> getChildren() {
		return children;
	}
	/**
	 * 设置 子类集合
	 * @param children
	 * 
	 */
	public void setChildren(Set<CatalogEntity> children) {
		this.children = children;
	}
	
	private String pageUrl;	//环球课程页面的地址

	/**
	 * 获取 环球页面的地址
	 * @return pageUrl
	 * 
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * 设置 环球课程页面的地址
	 * @param pageUrl
	 * 
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
}
