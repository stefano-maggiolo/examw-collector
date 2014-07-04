package com.examw.collector.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.examw.collector.dao.IListenEntityDao;
import com.examw.collector.domain.local.ListenEntity;
import com.examw.collector.model.RelateInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:18:26.
 */
public class ListenEntityDaoImpl extends BaseDaoImpl<ListenEntity> implements IListenEntityDao{
	/*
	 * 查询数据
	 */
	@Override
	public List<ListenEntity> findRelates(RelateInfo info) {
		String hql = "from Relate r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by r." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 */
	@Override
	public Long total(RelateInfo info) {
		String hql = "select count(*) from Relate r where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(RelateInfo info, String hql, Map<String, Object> parameters){
		if(info.getName() != null && !info.getName().trim().isEmpty()){
			hql += "  and (r.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		if(info.getClassId() != null && !info.getClassId().trim().isEmpty()){
			hql += "  and (r.grade.id = :classId)";
			parameters.put("classId", info.getClassId());
		}
		return hql;
	}
}
