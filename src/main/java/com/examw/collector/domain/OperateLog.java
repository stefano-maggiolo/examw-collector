package com.examw.collector.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * 操作日志类
 * @author fengwei.
 * @since 2014年7月13日 下午3:42:17.
 */
public class OperateLog implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id,account,ip,browser,content;
	private Date addTime;
	/**
	 * 获取 
	 * @return id
	 * 
	 */
	public String getId() {
		return id;
	}
	/**
	 * 设置 
	 * @param id
	 * 
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * 获取 
	 * @return account
	 * 
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * 设置 
	 * @param account
	 * 
	 */
	public void setAccount(String account) {
		this.account = account;
	}
	/**
	 * 获取 
	 * @return ip
	 * 
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * 设置 
	 * @param ip
	 * 
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * 获取 
	 * @return browser
	 * 
	 */
	public String getBrowser() {
		return browser;
	}
	/**
	 * 设置 
	 * @param browser
	 * 
	 */
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	/**
	 * 获取 
	 * @return content
	 * 
	 */
	public String getContent() {
		return content;
	}
	/**
	 * 设置 
	 * @param content
	 * 
	 */
	public void setContent(String content) {
		this.content = content;
	}
	/**
	 * 获取 
	 * @return addTime
	 * 
	 */
	public Date getAddTime() {
		return addTime;
	}
	/**
	 * 设置 
	 * @param addTime
	 * 
	 */
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	
	
}
