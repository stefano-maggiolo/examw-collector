package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.PackInfo;
import com.examw.model.DataGrid;

/**
 * 套餐服务接口
 * @author fengwei.
 * @since 2014年7月1日 上午9:58:16.
 */
public interface IPackService extends IBaseDataService<PackInfo>{
	/**
	 * 初始化导入数据
	 * @param info
	 */
	void init(PackInfo info);
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<PackInfo> dataGridUpdate(PackInfo info,String account);
	/**
	 * 更新集合
	 * @param info
	 */
	void update(List<PackInfo> packs);
}
