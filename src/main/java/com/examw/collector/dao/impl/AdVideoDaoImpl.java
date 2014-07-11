package com.examw.collector.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.examw.collector.dao.IAdVideoDao;
import com.examw.collector.domain.AdVideo;
import com.examw.collector.model.AdVideoInfo;

/**
 * 广告数据接口实现类
 * @author fengwei.
 * @since 2014年6月30日 下午4:37:17.
 */
public class AdVideoDaoImpl extends BaseDaoImpl<AdVideo> implements IAdVideoDao{
	@Override
	public List<AdVideo> findAdVideos(AdVideoInfo info) {
		String hql = "from AdVideo av where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		if(!StringUtils.isEmpty(info.getSort())){
			hql += " order by av." + info.getSort() + " " + info.getOrder();
		}
		return this.find(hql, parameters, info.getPage(), info.getRows());
	}
	/*
	 * 查询数据汇总。
	 */
	@Override
	public Long total(AdVideoInfo info) {
		String hql = "select count(*) from AdVideo av where 1 = 1 ";
		Map<String, Object> parameters = new HashMap<>();
		hql = this.addWhere(info, hql, parameters);
		return this.count(hql, parameters);
	}
	/**
	 * 添加查询条件到HQL。
	 * @param info
	 * 查询条件。
	 * @param hql
	 * HQL
	 * @param parameters
	 * 参数。
	 * @return
	 * HQL
	 */
	protected String addWhere(AdVideoInfo info,String hql,Map<String, Object> parameters){
		//名称查询
		if(!StringUtils.isEmpty(info.getName())){
			hql += " and (av.name like :name)";
			parameters.put("name", "%"+ info.getName() +"%");
		}
		return hql;
	}
}
