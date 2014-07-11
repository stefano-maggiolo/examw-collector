package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.Pack;
import com.examw.collector.model.PackInfo;

/**
 * 套餐数据接口
 * @author fengwei.
 * @since 2014年7月1日 上午9:55:58.
 */
public interface IPackDao extends IBaseDao<Pack>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<Pack> findPacks(PackInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(PackInfo info);
	/**
	 * 根据已存在的ID查找被删除的套餐
	 * @param existIds
	 * @return
	 */
	List<Pack> findDeletePacks(String existIds,PackInfo info);
}
