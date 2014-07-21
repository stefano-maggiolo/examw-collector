package com.examw.collector.service;
/**
 * 数据整体导入服务类
 * @author fengwei.
 * @since 2014年7月10日 上午9:50:01.
 */
public interface IImportDataService {
	/**
	 * 导入数据
	 * @param catalogId 课程分类的集合
	 */
	void init(String[] catalogId);
	
	void initAll();
}
