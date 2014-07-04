package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.model.SubClassInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午4:19:10.
 */
public interface IGradeEntityDao  extends IBaseDao<GradeEntity>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<GradeEntity> findSubClasses(SubClassInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubClassInfo info);

}
