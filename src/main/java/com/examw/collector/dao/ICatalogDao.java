package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.Catalog;
import com.examw.collector.model.CatalogInfo;

/**
 * 课程分类(副本)数据接口
 * @author fengwei.
 * @since 2014年6月30日 上午10:46:09.
 */
public interface ICatalogDao extends IBaseDao<Catalog>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<Catalog> findCatalogs(CatalogInfo info);
	/**
	 * 查询被删除的分类
	 * @param existIds	存在的ID
	 * @param pid	父类ID,为NULL表示顶级
	 * @return
	 */
	List<Catalog> findDeleteCatalogs(String existIds,String pid);
}	
