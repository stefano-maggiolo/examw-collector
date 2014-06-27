package com.examw.collector.domain;

import java.io.Serializable;
/**
 * 课程科目。
 * @author yangyong.
 * @since 2014-06-26.
 */
public class Subject implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,code,name;
	private Integer classTotal;
	private Catalog catalog;
	/**
	 * 获取所属课程。
	 * @return 所属课程。
	 */
	public Catalog getCatalog() {
		return catalog;
	}
	/**
	 * 设置所属课程。
	 * @param catalog
	 * 所属课程。
	 */
	public void setCatalog(Catalog catalog) {
		this.catalog = catalog;
	}
	/**
	 * 获取科目ID。
	 * @return 科目ID。
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置科目ID。
	 * @param id
	 * 科目ID。
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取科目代码。
	 * @return 科目代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置科目代码。
	 * @param code
	 * 科目代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取科目名称。
	 * @return 科目名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置科目名称。
	 * @param name
	 * 科目名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取科目下的班级数。
	 * @return 科目下的班级数。
	 */
	public Integer getClassTotal() {
		return classTotal;
	}
	/**
	 * 设置科目下的班级数。
	 * @param classTotal
	 * 科目下的班级数。
	 */
	public void setClassTotal(Integer classTotal) {
		this.classTotal = classTotal;
	}
}