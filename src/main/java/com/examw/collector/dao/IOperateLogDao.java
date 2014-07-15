package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.OperateLog;
import com.examw.collector.model.OperateLogInfo;

/**
 * 登录日志数据接口。
 * @author yangyong.
 * @since 2014-04-17.
 */
public interface IOperateLogDao extends IBaseDao<OperateLog> {
	/**
	 * 查询数据。
	 * @param info
	 * 查询条件。
	 * @return
	 * 结果数据。
	 */
	List<OperateLog> findOperateLogs(OperateLogInfo info);
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(OperateLogInfo info);
}