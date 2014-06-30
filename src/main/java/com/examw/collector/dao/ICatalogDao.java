package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.Catalog;
import com.examw.collector.model.CatalogInfo;

/**
 * 
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
}	
