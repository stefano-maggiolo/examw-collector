package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.SubjectInfo;

/**
 * 科目信息服务接口
 * @author fengwei.
 * @since 2014年6月30日 下午3:53:58.
 */
public interface ISubjectEntityService extends IBaseDataService<SubjectInfo>{
	/**
	 * 初始化导入数据
	 */
	void init(SubjectInfo info);
	/**
	 * 更新一个集合
	 * @param subjects
	 */
	void update(List<SubjectInfo> subjects);
}
