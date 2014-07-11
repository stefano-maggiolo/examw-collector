package com.examw.collector.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IAdVideoDao;
import com.examw.collector.domain.AdVideo;
import com.examw.collector.model.AdVideoInfo;
import com.examw.collector.service.IAdVideoService;

/**
 * 广告服务接口实现类
 * @author fengwei.
 * @since 2014年7月1日 上午10:31:00.
 */
public class AdVideoServiceImpl extends BaseDataServiceImpl<AdVideo, AdVideoInfo> implements IAdVideoService{
	private IAdVideoDao adVideoDao;
	
	/**
	 * 设置 广告数据接口
	 * @param adVideoDao
	 * 
	 */
	public void setAdVideoDao(IAdVideoDao adVideoDao) {
		this.adVideoDao = adVideoDao;
	}

	@Override
	protected List<AdVideo> find(AdVideoInfo info) {
		return this.adVideoDao.findAdVideos(info);
	}

	@Override
	protected AdVideoInfo changeModel(AdVideo data) {
		if(data == null) return null;
		AdVideoInfo info = new AdVideoInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	@Override
	protected Long total(AdVideoInfo info) {
		return this.adVideoDao.total(info);
	}

	@Override
	public AdVideoInfo update(AdVideoInfo info) {
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	

}
