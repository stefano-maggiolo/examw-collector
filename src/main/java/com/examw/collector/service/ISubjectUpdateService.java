package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.SubjectInfo;
import com.examw.model.DataGrid;

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
	List<SubjectInfo> update(List<SubjectInfo> subjects,String account);
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<SubjectInfo> dataGridUpdate(String account);
}
