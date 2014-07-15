package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.CatalogInfo;
import com.examw.model.DataGrid;
import com.examw.model.TreeNode;

/**
 * 课程分类服务接口
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
	/**
	 * 找出有变化的集合
	 * @return
	 */
//	Map<String,Object> findChanged();
	/**
	 * 获取新增或更新的集合
	 * @return
	 */
	DataGrid<CatalogInfo> dataGridUpdate(String account);
	/**
	 * 更新一个集合
	 * @param subjects
	 */
	void update(List<CatalogInfo> catalogs,String account);
}
