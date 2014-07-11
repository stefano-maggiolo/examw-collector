package com.examw.collector.dao.impl;

import java.util.List;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.model.CatalogInfo;

/**
 * 课程分类数据接口实现类
 * @author fengwei.
 * @since 2014年6月30日 上午10:47:19.
 */
public class CatalogDaoImpl extends BaseDaoImpl<Catalog> implements ICatalogDao{

	@Override
	public List<Catalog> findCatalogs(CatalogInfo info) {
		String hql =  "from Catalog c  where c.parent is null order by c.code";
		return this.find(hql, null, null, null);
	}
	
	@Override
	public List<Catalog> findDeleteCatalogs(String existIds, String pid) {
		String hql = "from Catalog c where c.id not in ("+existIds+")";
		if(pid == null)
			hql += " and c.parent is null";
		else
			hql += " and c.parent.id = '"+pid+"'";
		return this.find(hql, null, null, null);
	}
}
