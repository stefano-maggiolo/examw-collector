package com.examw.collector.service;

import com.examw.collector.model.PackInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:58:16.
 */
public interface IPackService extends IBaseDataService<PackInfo>{
	/**
	 * 初始化导入数据
	 * @param info
	 */
	void init(PackInfo info);
}
