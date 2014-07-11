package com.examw.collector.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.examw.collector.dao.ITeacherEntityDao;
import com.examw.collector.domain.local.TeacherEntity;
import com.examw.collector.model.TeacherInfo;

/**
 * 老师数据接口实现类
 * @author fengwei.
 * @since 2014年7月9日 上午11:20:35.
 */
public class TeacherEntityDaoImpl  extends BaseDaoImpl<TeacherEntity> implements ITeacherEntityDao{
	/*
	 * 查询数据
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#findClassTypes(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public List<TeacherEntity> findTeachers(TeacherInfo info) {
		String hql = "from TeacherEntity t where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by t." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#total(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public Long total(TeacherInfo info) {
		String hql = "select count(*) from TeacherEntity t where 1=1 ";
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
	protected String addWhere(TeacherInfo info, String hql, Map<String, Object> parameters){
		if(info.getName() != null && !info.getName().trim().isEmpty()){
			hql += "  and (t.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		if(!StringUtils.isEmpty(info.getCatalogId())){
			hql += " and (t.catalogId in (:catalogId))";
			parameters.put("catalogId", info.getCatalogId());
		}
		return hql;
	}
}
