package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.local.TeacherEntity;
import com.examw.collector.model.TeacherInfo;

/**
 * 老师数据接口
 * @author fengwei.
 * @since 2014年7月9日 上午11:17:45.
 */
public interface ITeacherEntityDao extends IBaseDao<TeacherEntity>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<TeacherEntity> findTeachers(TeacherInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(TeacherInfo info);
}
