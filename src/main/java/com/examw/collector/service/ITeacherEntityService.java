package com.examw.collector.service;

import com.examw.collector.model.TeacherInfo;

/**
 * 老师服务接口
 * @author fengwei.
 * @since 2014年7月9日 上午11:25:06.
 */
public interface ITeacherEntityService extends IBaseDataService<TeacherInfo>{
	/**
	 * 初始化导入数据
	 * @param info
	 */
	void init(TeacherInfo info);

}
