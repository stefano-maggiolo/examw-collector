package com.examw.collector.domain.local;

import java.io.Serializable;

/**
 * 套餐数据
 * @author fengwei.
 * @since 2014年6月28日 下午5:08:45.
 */
public class PackageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private String classCodes;
	private Integer source,discount,studentPrice;
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
	public String getClassCodes() {
		return classCodes;
	}
	/**
	 * 设置 包含班级ID
	 * @param classIds
	 * 包含班级ID
	 */
	public void setClassCodes(String classIds) {
		this.classCodes = classIds;
	}
	
	private SubjectEntity subjectEntity;
	private CatalogEntity catalogEntity;
	/**
	 * 获取 科目
	 * @return subjectEntity
	 * 
	 */
	public SubjectEntity getSubjectEntity() {
		return subjectEntity;
	}
	/**
	 * 设置 科目
	 * @param subjectEntity
	 * 
	 */
	public void setSubjectEntity(SubjectEntity subjectEntity) {
		this.subjectEntity = subjectEntity;
	}
	/**
	 * 获取 考试分类
	 * @return catalogEntity
	 * 
	 */
	public CatalogEntity getCatalogEntity() {
		return catalogEntity;
	}
	/**
	 * 设置 考试分类
	 * @param catalogEntity
	 * 
	 */
	public void setCatalogEntity(CatalogEntity catalogEntity) {
		this.catalogEntity = catalogEntity;
	}
	/**
	 * 获取 老学员价
	 * @return studentPrice
	 * 
	 */
	public Integer getStudentPrice() {
		return studentPrice;
	}
	/**
	 * 设置 老学员价
	 * @param studentPrice
	 * 
	 */
	public void setStudentPrice(Integer studentPrice) {
		this.studentPrice = studentPrice;
	}
}