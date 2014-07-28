package com.examw.collector.service;

import com.examw.collector.domain.UpdateLog;

/**
 * 定时器更新日志服务接口
 * @author fengwei.
 * @since 2014年7月28日 下午4:26:23.
 */
public interface IUpdateLogService {
	/**
	 * 保存记录
	 */
	void save(UpdateLog log);
}
