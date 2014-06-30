package com.examw.collector.dao.impl;

import java.util.List;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.model.CatalogInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午10:47:19.
 */
public class CatalogDaoImpl extends BaseDaoImpl<Catalog> implements ICatalogDao{

	@Override
	public List<Catalog> findCatalogs(CatalogInfo info) {
		String hql =  "from Catalog c  where c.parent is null order by c.code";
		return this.find(hql, null, null, null);
	}
}
