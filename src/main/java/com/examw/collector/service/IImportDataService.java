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
	/**
	 * 导入所有
	 */
	void initAll();
	/**
	 * 获取实际分类中所有的环球code,用","分割
	 * @return
	 */
	String getIds();
	
	void initAllTeacher();
}
