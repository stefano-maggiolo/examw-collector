package com.examw.collector.entity;

import java.io.Serializable;

/**
 * 
 * @author fengwei.
 * @since 2014年6月28日 下午5:08:45.
 */
public class PackageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private String classIds;
	private Integer source,discount;
	/**
	 * 获取套餐编码。
	 * @return 套餐编码。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置套餐编码。
	 * @param code
	 * 套餐编码。
	 */
	public void setId(String id) {
		this.id = id;
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
	 * 获取 包含班级ID
	 * @return classIds
	 * 包含班级ID
	 */
	public String getClassIds() {
		return classIds;
	}
	/**
	 * 设置 包含班级ID
	 * @param classIds
	 * 包含班级ID
	 */
	public void setClassIds(String classIds) {
		this.classIds = classIds;
	}
	
}