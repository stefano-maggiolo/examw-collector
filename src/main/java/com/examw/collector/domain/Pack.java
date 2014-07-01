package com.examw.collector.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * 套餐。
 * @author yangyong.
 * @since 2014-06-28.
 */
public class Pack implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name;
	private String classCodes;
	private Integer source,discount;
	private boolean isShow;
	/**
	 * 获取套餐编码。
	 * @return 套餐编码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置套餐编码。
	 * @param code
	 * 套餐编码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取套餐名称。
	 * @return 套餐名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置套餐名称。
	 * @param name
	 * 套餐名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取套餐的原价。
	 * @return 套餐的原价。
	 */
	public Integer getSource() {
		return source;
	}
	/**
	 * 设置套餐的原价。
	 * @param source
	 * 套餐的原价。
	 */
	public void setSource(Integer source) {
		this.source = source;
	}
	/**
	 * 获取套餐的售价。
	 * @return 套餐的售价。
	 */
	public Integer getDiscount() {
		return discount;
	}
	/**
	 * 设置套餐的售价。
	 * @param discount
	 * 套餐的售价。
	 */
	public void setDiscount(Integer discount) {
		this.discount = discount;
	}
	/**
	 * 获取是否显示。
	 * @return 是否显示。
	 */
	public boolean getIsShow() {
		return isShow;
	}
	/**
	 * 设置是否显示。
	 * @param isShow
	 * 是否显示。
	 */
	public void setIsShow(boolean isShow) {
		this.isShow = isShow;
	}
	/**
	 * 获取套餐所包含的班别编码数组。
	 * @return 套餐所包含的班别编码数组。
	 */
	public String getClassCodes() {
		return classCodes;
	}
	/**
	 * 设置套餐所包含的班别编码数组。
	 * @param classCodes
	 * 套餐所包含的班别编码数组。
	 */
	public void setClassCodes(String classCodes) {
		this.classCodes = classCodes;
	}
	
	//科目
	private Subject subject;
	/**
	 * 获取 科目
	 * @return subject
	 * 
	 */
	public Subject getSubject() {
		return subject;
	}
	/**
	 * 设置 科目
	 * @param subject
	 * 
	 */
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	//分类
	private Catalog catalog;
	/**
	 * 获取 分类
	 * @return catalog
	 * 分类
	 */
	public Catalog getCatalog() {
		return catalog;
	}
	/**
	 * 设置 分类
	 * @param catalog
	 * 分类
	 */
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	//包含班级
	private Set<SubClass> subClasses;
	/**
	 * 获取 包含班级
	 * @return subClasses
	 * 包含班级
	 */
	public Set<SubClass> getSubClasses() {
		return subClasses;
	}
	/**
	 * 设置 包含班级
	 * @param subClasses
	 * 包含班级
	 */
	public void setSubClasses(Set<SubClass> subClasses) {
		this.subClasses = subClasses;
	}
	
}