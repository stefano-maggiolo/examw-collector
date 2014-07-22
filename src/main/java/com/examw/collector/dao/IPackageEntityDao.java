package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.local.PackageEntity;
import com.examw.collector.model.PackInfo;

/**
 * 套餐数据接口
 * @author fengwei.
 * @since 2014年7月1日 上午9:55:58.
 */
public interface IPackageEntityDao extends IBaseDao<PackageEntity>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<PackageEntity> findPacks(PackInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(PackInfo info);
	/**
	 * 根据科目id删除班级
	 * @param subjectId
	 */
	void delete(String subjectId);
}
