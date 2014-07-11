package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.Relate;
import com.examw.collector.model.RelateInfo;

/**
 * 课节数据接口
 * @author fengwei.
 * @since 2014年7月1日 上午9:17:31.
 */
public interface IRelateDao extends IBaseDao<Relate>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<Relate> findRelates(RelateInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(RelateInfo info);
	/**
	 * 根据班级ID删除课节
	 * @param classId
	 */
	void delete(String classId);
}
