package com.examw.collector.dao;

import com.examw.collector.domain.ErrorRecord;

/**
 * 错误记录数据接口
 * @author fengwei.
 * @since 2014年7月28日 上午10:21:14.
 */
public interface IErrorRecordDao extends IBaseDao<ErrorRecord> {
	/**
	 * 根据数据ID,以及类型[班级or套餐]查找错误记录
	 * @param dataId
	 * @param type
	 * @return
	 */
	ErrorRecord find(String dataId,Integer type);
}
