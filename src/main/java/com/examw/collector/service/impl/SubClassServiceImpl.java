package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IAdVideoDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.domain.AdVideo;
import com.examw.collector.domain.SubClass;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.ISubClassService;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午4:34:44.
 */
public class SubClassServiceImpl extends BaseDataServiceImpl<SubClass, SubClassInfo> implements ISubClassService{
	private static Logger logger = Logger.getLogger(SubClassServiceImpl.class);
	private ISubClassDao subClassDao;
	private IAdVideoDao adVideoDao;
	private IDataServer dataServer;
	
	/**
	 * 设置 班级数据接口
	 * @param subClassDao
	 * 
	 */
	public void setSubClassDao(ISubClassDao subClassDao) {
		this.subClassDao = subClassDao;
	}
	/**
	 * 设置 广告数据接口
	 * @param adVideoDao
	 * 
	 */
	public void setAdVideoDao(IAdVideoDao adVideoDao) {
		this.adVideoDao = adVideoDao;
	}
	/**
	 * 设置 远程数据接口
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
	}
	@Override
	protected List<SubClass> find(SubClassInfo info) {
		return this.subClassDao.findSubClasses(info);
	}
	@Override
	protected SubClassInfo changeModel(SubClass data) {
		if(data == null) return null;
		SubClassInfo info = new SubClassInfo();
		BeanUtils.copyProperties(data, info);
		//设置科目
		if(data.getSubject() != null){
			info.setSubjectId(data.getSubject().getCode());
			info.setSubjectName(data.getSubject().getName());
		}
		if(data.getCatalog() != null){
			info.setCatalogId(data.getCatalog().getCode());
			info.setCatalogName(data.getCatalog().getName());
		}
		//设置机构
		if(data.getAdVideo()!=null){
			info.setAdVideoId(data.getAdVideo().getCode());
			info.setAdVideoName(data.getAdVideo().getName());
		}
		return info;
	}
	@Override
	protected Long total(SubClassInfo info) {
		return this.subClassDao.total(info);
	}
	@Override
	public SubClassInfo update(SubClassInfo info) {
		
		return null;
	}
	@Override
	public void delete(String[] ids) {
	}
	
	@Override
	public void init(SubClassInfo info) {
		if(info == null) return;
		logger.info("开始初始化班级...");
		List<SubClass> data = this.dataServer.loadClasses(info.getCatalogId(), info.getSubjectId());
		if(data!=null &&data.size()>0)
		{
			this.adVideoDao.batchSave(getAdVideos(data));
			this.subClassDao.batchSave(data);
		}
		logger.info("初始化完成！");
	}
	private List<AdVideo> getAdVideos(List<SubClass> list){
		List<AdVideo> data = new ArrayList<AdVideo>();
		for(SubClass s : list){
			if(s.getAdVideo()!=null){
				data.add(s.getAdVideo());
			}
		}
		return data;
	}
}
