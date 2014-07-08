package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.SubClass;
import com.examw.collector.model.SubClassInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午4:19:10.
 */
public interface ISubClassDao  extends IBaseDao<SubClass>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<SubClass> findSubClasses(SubClassInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(SubClassInfo info);
	/**
	 * 根据已存在的ID查找被删除的班级
	 * @param existIds
	 * @return
	 */
	List<SubClass> findDeleteSubClasss(String existIds,SubClassInfo info);
}
