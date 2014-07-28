package com.examw.collector.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.examw.collector.dao.IErrorRecordDao;
import com.examw.collector.domain.ErrorRecord;

/**
 * 错误记录数据接口实现类
 * @author fengwei.
 * @since 2014年7月28日 上午10:26:03.
 */
public class ErrorRecordDaoImpl extends BaseDaoImpl<ErrorRecord> implements
		IErrorRecordDao {
	
	@Override
	public ErrorRecord find(String dataId, Integer type) {
		String hql = "from ErrorRecord where dataId = :dataId and type = :type";
		Query query = this.getCurrentSession().createQuery(hql);
		query.setParameter("dataId", dataId);
		query.setParameter("type", type);
		@SuppressWarnings("unchecked")
		List<ErrorRecord> list = query.list();
		if(list == null || list.size()==0)
			return null;
		return list.get(0);
	}
	
}
