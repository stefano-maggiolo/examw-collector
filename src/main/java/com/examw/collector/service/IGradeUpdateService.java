package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.SubClassInfo;

/**
 * 班级数据更新服务接口
 * @author fengwei.
 * @since 2014年7月9日 下午5:00:44.
 */
public interface IGradeUpdateService {
	/**
	 * 更新数据集合
	 * @param subClasses
	 */
	void update(List<SubClassInfo> subClasses);
}
