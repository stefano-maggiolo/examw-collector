package com.examw.collector.domain;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.examw.collector.support.CustomDateSerializer;

/**
 * 操作日志类
 * @author fengwei.
 * @since 2014年7月13日 下午3:42:17.
 */
public class OperateLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,account,content,name;
	private Date addTime;
	private Integer type;
	public static final int TYPE_COMPARECODE = 0,	//比较代码
						TYPE_UPDATE_CATALOG = 1,	//更新课程分类
					TYPE_UPDATE_SUBJECT = 2,		//更新科目
				TYPE_UPDATE_GRADE = 3,				//更新班级
			TYPE_UPDATE_PACKAGE =4;					//更新套餐
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
	 * 获取 帐号
	 * @return account
	 * 
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置 帐号
	 * @param account
	 * 
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取 内容
	 * @return content
	 * 
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 内容
	 * @param content
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 添加时间
	 * @return addTime
	 * 
	 */
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * 设置 添加时间
	 * @param addTime
	 * 
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	/**
	 * 获取 类型
	 * @return type
	 * 
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置 类型
	 * @param type
	 * 
	 */
	public void setType(Integer type) {
		this.type = type;
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
	
}
