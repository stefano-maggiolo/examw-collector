package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.SubClassInfo;
import com.examw.model.DataGrid;

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
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<SubClassInfo> dataGridUpdate(SubClassInfo info);
	/**
	 * 更新一个集合
	 * @param subClasses
	 */
	void update(List<SubClassInfo> subClasses);
}
