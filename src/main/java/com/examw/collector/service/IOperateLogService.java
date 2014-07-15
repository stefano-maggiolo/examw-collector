package com.examw.collector.service;

import java.util.Map;

import com.examw.collector.model.OperateLogInfo;

/**
 * 登录日志服务接口。
 * @author yangyong.
 * @since 2014-05-17.
 */
public interface IOperateLogService extends IBaseDataService<OperateLogInfo> {
	/**
	 * 添加日志。
	 * @param account
	 * 登录账号。
	 * @param ip
	 * 登录IP。
	 * @param browser
	 * 浏览器信息。
	 */
	void addLog(String account,String ip,String browser);
	/**
	 * 获取日志类型的名称
	 * @param type
	 * @return
	 */
	String getTypeName(Integer type);
	/**
	 * 获取类型名称映射
	 * @return
	 */
	Map<String,String> getTypeMap();
}