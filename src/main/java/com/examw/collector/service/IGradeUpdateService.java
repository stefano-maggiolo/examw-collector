package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.SubClassInfo;
import com.examw.model.DataGrid;

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
	List<SubClassInfo> update(List<SubClassInfo> subClasses,String account);
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<SubClassInfo> dataGridUpdate(String account);
	/**
	 * 用于定时器更新
	 * @param account
	 * @return
	 */
	List<SubClassInfo> update(String account);
}
