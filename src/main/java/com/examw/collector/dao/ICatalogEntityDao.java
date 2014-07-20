package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.model.local.CatalogEntityInfo;

/**
 * 课程分类接口
 * @author fengwei.
 * @since 2014年7月2日 上午9:58:17.
 */
public interface ICatalogEntityDao extends IBaseDao<CatalogEntity>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<CatalogEntity> findCatalogs(CatalogEntityInfo info);
	/**
	 * 根据环球的code找考试分类
	 * @param edu24_code
	 * @return
	 */
	CatalogEntity find(String edu24_code);
	/**
	 * 返回带有环球code的所有考试分类
	 * @return
	 */
	List<CatalogEntity> findAllWithCode();
}
