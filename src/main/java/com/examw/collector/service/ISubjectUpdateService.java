package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.SubjectInfo;

/**
 * 科目数据更新服务接口
 * @author fengwei.
 * @since 2014年7月9日 下午4:46:19.
 */
public interface ISubjectUpdateService {
	/**
	 * 数据更新
	 * @param subjects
	 */
	void update(List<SubjectInfo> subjects);
}
