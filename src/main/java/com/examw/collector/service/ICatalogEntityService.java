package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.local.CatalogEntityInfo;
import com.examw.model.TreeNode;

/**
 * 课程分类服务接口
 * @author fengwei.
 * @since 2014年7月2日 上午10:00:14.
 */
public interface ICatalogEntityService extends IBaseDataService<CatalogEntityInfo>{
	/**
	 * 加载考试树
	 * @return
	 */
	List<TreeNode> loadAllCatalogs();
	/**
	 * 更新一个集合
	 * @param subjects
	 */
//	void update(List<CatalogInfo> catalogs);
}
