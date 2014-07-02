package com.examw.collector.dao.impl;

import java.util.List;

import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.model.local.CatalogEntityInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年7月2日 上午9:59:12.
 */
public class CatalogEntityDaoImpl extends BaseDaoImpl<CatalogEntity> implements ICatalogEntityDao{

	@Override
	public List<CatalogEntity> findCatalogs(CatalogEntityInfo info) {
		String hql =  "from CatalogEntity c  where c.parent.id = 0 order by c.id";
		return this.find(hql, null, null, null);
	}
}
