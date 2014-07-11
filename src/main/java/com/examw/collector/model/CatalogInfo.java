package com.examw.collector.model;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.model.Paging;

/**
 * 课节信息
 * @author fengwei.
 * @since 2014年6月30日 上午11:01:42.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class CatalogInfo extends Paging implements Serializable{
	private static final long serialVersionUID = 1L;
	private String pid,id,name,status;
	private Integer classTotal;
	private List<CatalogInfo> children;
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
	 * 获取 名称
	 * @return name
	 * 
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置 名称
	 * @param name
	 * 
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取 课程数量
	 * @return classTotal
	 * 
	 */
	public Integer getClassTotal() {
		return classTotal;
	}
	/**
	 * 设置 课程数量
	 * @param classTotal
	 * 
	 */
	public void setClassTotal(Integer classTotal) {
		this.classTotal = classTotal;
	}
	/**
	 * 获取 子类
	 * @return children
	 * 
	 */
	public List<CatalogInfo> getChildren() {
		return children;
	}
	/**
	 * 设置 子类
	 * @param children
	 * 
	 */
	public void setChildren(List<CatalogInfo> children) {
		this.children = children;
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
	
	/*
	 * 配合前台传过来的数据,没有实际的作用
	 */
	private String state;
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
