package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.PackInfo;
import com.examw.model.DataGrid;

/**
 * 套餐更新服务接口
 * @author fengwei.
 * @since 2014年7月9日 下午5:01:14.
 */
public interface IPackageUpdateService {
	/**
	 * 更新集合
	 * @param info
	 * @return 返回实际更新的集合
	 */
	List<PackInfo> update(List<PackInfo> packs,String account);
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<PackInfo> dataGridUpdate(String account);
}
