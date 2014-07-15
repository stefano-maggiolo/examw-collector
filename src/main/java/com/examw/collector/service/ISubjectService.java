package com.examw.collector.service;

import java.util.List;
import java.util.Map;

import com.examw.collector.model.SubjectInfo;
import com.examw.model.DataGrid;

/**
 * 科目信息服务接口
 * @author fengwei.
 * @since 2014年6月30日 下午3:53:58.
 */
public interface ISubjectService extends IBaseDataService<SubjectInfo>{
	/**
	 * 找出有变化的集合
	 * @return
	 */
	Map<String,Object> findChanged();
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<SubjectInfo> dataGridUpdate(String account);
	/**
	 * 更新一个集合
	 * @param subjects
	 */
	void update(List<SubjectInfo> subjects);
}
