package com.examw.collector.domain;

import java.io.Serializable;
import java.util.Set;

/**
 * 课程类别。
 * @author yangyong.
 * @since 2014-06-26.
 */
public class Catalog extends BaseDomain implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name;
	private Integer classTotal;
	private Catalog parent;
	private Set<Subject> subjects;
	private Set<Catalog> children;
	/**
	 * 获取上级课程类别。
	 * @return 上级课程类别。
	 */
	public Catalog getParent() {
		return parent;
	}
	/**
	 * 设置上级课程类别。
	 * @param parent
	 * 上级课程类别。
	 */
	public void setParent(Catalog parent) {
		this.parent = parent;
	}
	/**
	 * 获取课程类别代码。
	 * @return 课程类别代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 设置课程类别代码。
	 * @param code
	 * 课程类别代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取课程类别名称。
	 * @return 课程类别名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置课程类别名称。
	 * @param name
	 * 课程类别名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取班级数目。
	 * @return
	 * 班级数目。
	 */
	public Integer getClassTotal() {
		return classTotal;
	}
	/**
	 * 设置班级数目。
	 * @param classTotal
	 * 班级数目。
	 */
	public void setClassTotal(Integer classTotal) {
		this.classTotal = classTotal;
	}
	/**
	 * 获取科目集合。
	 * @return 科目集合。
	 */
	public Set<Subject> getSubjects() {
		return subjects;
	}
	/**
	 * 设置科目集合。
	 * @param subjects
	 * 科目集合。
	 */
	public void setSubjects(Set<Subject> subjects) {
		this.subjects = subjects;
	}
	/**
	 * 获取子课程类别集合。
	 * @return 子课程类别集合。
	 */
	public Set<Catalog> getChildren() {
		return children;
	}
	/**
	 * 设置子课程类别集合。
	 * @param children
	 * 子课程类别集合。
	 */
	public void setChildren(Set<Catalog> children) {
		this.children = children;
	}
	/* 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parent == null) ? 0 : parent.hashCode());
		return result;
	}
	/* 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Catalog other = (Catalog) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (parent == null) {
			if (other.parent != null)
				return false;
		} else if (!parent.equals(other.parent))
			return false;
		return true;
	}
}