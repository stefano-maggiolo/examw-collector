package com.examw.collector.service.impl;

import com.examw.collector.dao.IUpdateLogDao;
import com.examw.collector.domain.UpdateLog;
import com.examw.collector.service.IUpdateLogService;

/**
 * 定时器更新日志服务接口实现类
 * @author fengwei.
 * @since 2014年7月28日 下午4:26:46.
 */
public class UpdateLogServiceImpl implements IUpdateLogService{
	private IUpdateLogDao updateLogDao;

	/**
	 * 设置 更新日志数据接口
	 * @param updateLogDao
	 * 
	 */
	public void setUpdateLogDao(IUpdateLogDao updateLogDao) {
		this.updateLogDao = updateLogDao;
	}
	@Override
	public void save(UpdateLog data) {
		this.updateLogDao.save(data);
	}
}
