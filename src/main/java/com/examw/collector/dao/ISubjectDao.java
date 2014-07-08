package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.Subject;
import com.examw.collector.model.SubjectInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午2:41:03.
 */
public interface ISubjectDao extends IBaseDao<Subject>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<Subject> findSubjects(SubjectInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubjectInfo info);
	/**
	 * 根据已存在的ID查找被删除的科目
	 * @param existIds
	 * @return
	 */
	List<Subject> findDeleteSubjects(String existIds);
}
