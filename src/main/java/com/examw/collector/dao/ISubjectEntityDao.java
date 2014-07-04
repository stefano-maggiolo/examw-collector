package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubjectInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午2:41:03.
 */
public interface ISubjectEntityDao extends IBaseDao<SubjectEntity>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<SubjectEntity> findSubjects(SubjectInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubjectInfo info);
}
