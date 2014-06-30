package com.examw.collector.service;

import com.examw.collector.model.SubClassInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午4:33:11.
 */
public interface ISubClassService  extends IBaseDataService<SubClassInfo>{
	/**
	 * 初始化导入数据
	 * @param info
	 */
	void init(SubClassInfo info);
}
