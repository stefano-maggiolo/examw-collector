package com.examw.collector.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.model.local.CatalogEntityInfo;

/**
 * 课程分类数据接口实现类
 * @author fengwei.
 * @since 2014年7月2日 上午9:59:12.
 */
public class CatalogEntityDaoImpl extends BaseDaoImpl<CatalogEntity> implements ICatalogEntityDao{

	@Override
	public List<CatalogEntity> findCatalogs(CatalogEntityInfo info) {
		String hql =  "from CatalogEntity c  where c.parent.id = 0 order by c.id";
		return this.find(hql, null, null, null);
	}
	
	@Override
	public CatalogEntity find(String edu24_code) {
		//使用charindex函数 
		String hql = "from CatalogEntity c where c.code = :code or charindex(:code,c.code) > 0";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("code", edu24_code);
		@SuppressWarnings("unchecked")
		List<CatalogEntity> list = query.list();
		if(list == null || list.size()==0)
			return null;
		return list.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CatalogEntity> findAllWithCode() {
		//只用一个进行测试测试用
		//String hql = "from CatalogEntity c where c.code in('1235')";
		String hql = "from CatalogEntity c where c.code is not null and c.code != ''";
		Query query = this.getCurrentSession().createQuery(hql);
		return query.list();
	}
}
