package com.examw.collector.dao;

import java.util.List;

import com.examw.collector.domain.AdVideo;
import com.examw.collector.model.AdVideoInfo;

/**
 * 广告数据接口
 * @author fengwei.
 * @since 2014年6月30日 下午4:37:03.
 */
public interface IAdVideoDao extends IBaseDao<AdVideo>{
	/**
	 * 查询分类数据。
	 * @return
	 * 结果数据。
	 */
	List<AdVideo> findAdVideos(AdVideoInfo info);
	
	/**
	 * 查询数据总数。
	 * @param info
	 * 查询条件。
	 * @return
	 * 数据总数。
	 */
	Long total(AdVideoInfo info);
}
