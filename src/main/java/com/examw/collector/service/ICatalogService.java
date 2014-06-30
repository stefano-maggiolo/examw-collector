package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.CatalogInfo;
import com.examw.model.TreeNode;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午10:57:15.
 */
public interface ICatalogService extends IBaseDataService<CatalogInfo>{
	/**
	 * 初始化导入数据
	 */
	void init();
	/**
	 * 加载考试树
	 * @return
	 */
	List<TreeNode> loadAllCatalogs();
}
