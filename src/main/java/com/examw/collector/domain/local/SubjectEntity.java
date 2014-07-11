package com.examw.collector.domain.local;

import java.io.Serializable;

/**
 * 科目数据
 * @author fengwei.
 * @since 2014年6月28日 下午5:08:19.
 */
public class SubjectEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,name;
	private String fudao;
	private CatalogEntity catalogEntity;
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
	 * 获取 
	 * @return fudao
	 * 
	 */
	public String getFudao() {
		return fudao;
	}
	/**
	 * 设置 
	 * @param fudao
	 * 
	 */
	public void setFudao(String fudao) {
		this.fudao = fudao;
	}
	/**
	 * 获取 分类
	 * @return catalogEntity
	 * 
	 */
	public CatalogEntity getCatalogEntity() {
		return catalogEntity;
	}
	/**
	 * 设置 分类
	 * @param catalogEntity
	 * 
	 */
	public void setCatalogEntity(CatalogEntity catalogEntity) {
		this.catalogEntity = catalogEntity;
	}
	
}
