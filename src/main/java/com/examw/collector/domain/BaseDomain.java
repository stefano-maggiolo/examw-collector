package com.examw.collector.domain;
/**
 * 基础类
 * @author fengwei.
 * @since 2014年7月6日 下午1:52:02.
 */
public class BaseDomain {
	protected String status;
	protected String updateInfo;

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

	/**
	 * 获取 更新信息
	 * @return updateInfo
	 * 
	 */
	public String getUpdateInfo() {
		return updateInfo;
	}

	/**
	 * 设置 更新信息
	 * @param updateInfo
	 * 
	 */
	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}
	
}
