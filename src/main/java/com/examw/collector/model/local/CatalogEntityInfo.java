package com.examw.collector.model.local;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午11:01:42.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CatalogEntityInfo extends Paging implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid,id,cname,ename,code,schoolId;
	private Integer childNum;
	private List<CatalogEntityInfo> children;
	/**
	 * 获取 父ID
	 * @return pid
	 * 
	 */
	public String getPid() {
		return pid;
	}
	/**
	 * 设置 父ID
	 * @param pid
	 * 
	 */
	public void setPid(String pid) {
		this.pid = pid;
	}
	
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
	 * 获取 子类
	 * @return children
	 * 
	 */
	public List<CatalogEntityInfo> getChildren() {
		return children;
	}
	/**
	 * 设置 子类
	 * @param children
	 * 
	 */
	public void setChildren(List<CatalogEntityInfo> children) {
		this.children = children;
	}
	/**
	 * 获取 环球代码
	 * @return code
	 * 
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置 环球代码
	 * @param code
	 * 
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * 获取 子类别数
	 * @return childNum
	 * 
	 */
	public Integer getChildNum() {
		return childNum;
	}
	/**
	 * 设置 子类别数
	 * @param childNum
	 * 
	 */
	public void setChildNum(Integer childNum) {
		this.childNum = childNum;
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
	
	private String pageUrl;
	/**
	 * 获取 
	 * @return pageUrl
	 * 
	 */
	public String getPageUrl() {
		return pageUrl;
	}
	/**
	 * 设置 
	 * @param pageUrl
	 * 
	 */
	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
	
}
