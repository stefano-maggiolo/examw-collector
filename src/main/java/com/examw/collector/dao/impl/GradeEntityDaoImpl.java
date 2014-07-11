package com.examw.collector.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.collector.dao.IGradeEntityDao;
import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.model.SubClassInfo;

/**
 * 班级数据接口实现类
 * @author fengwei.
 * @since 2014年6月30日 下午4:21:44.
 */
public class GradeEntityDaoImpl extends BaseDaoImpl<GradeEntity> implements IGradeEntityDao{

	/*
	 * 查询数据
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#findClassTypes(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public List<GradeEntity> findSubClasses(SubClassInfo info) {
		String hql = "from GradeEntity sc where 1=1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(info.getSort() != null && !info.getSort().trim().isEmpty()){
			hql += " order by sc." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据统计。
	 * @see com.examw.netplatform.dao.admin.IClassTypeDao#total(com.examw.netplatform.model.admin.ClassTypeInfo)
	 */
	@Override
	public Long total(SubClassInfo info) {
		String hql = "select count(*) from GradeEntity sc where 1=1 ";
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
	protected String addWhere(SubClassInfo info, String hql, Map<String, Object> parameters){
		if(info.getName() != null && !info.getName().trim().isEmpty()){
			hql += "  and (sc.name like :name)";
			parameters.put("name", "%" + info.getName()+ "%");
		}
		if(info.getSubjectId() != null && !info.getSubjectId().trim().isEmpty()){
			hql += "  and (sc.subjectEntity.id = :subjectId)";
			parameters.put("subjectId", info.getSubjectId());
		}
		if(!StringUtils.isEmpty(info.getCatalogId()))
		{
			hql += "  and (sc.subjectEntity.catalogEntity.code = :catalogId)";
			parameters.put("catalogId", info.getCatalogId());
		}
		return hql;
	}
}
