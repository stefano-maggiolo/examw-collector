package com.examw.collector.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IRelateDao;
import com.examw.collector.domain.Relate;
import com.examw.collector.model.RelateInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IRelateService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:24:19.
 */
public class RelateServiceImpl extends BaseDataServiceImpl<Relate, RelateInfo> implements IRelateService{
	private static Logger logger = Logger.getLogger(RelateServiceImpl.class);
	private IRelateDao relateDao;
	private IDataServer dataServer;
	
	/**
	 * 设置 课节数据接口
	 * @param relateDao
	 * 
	 */
	public void setRelateDao(IRelateDao relateDao) {
		this.relateDao = relateDao;
	}
	
	/**
	 * 设置  远程数据接口
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
	}

	@Override
	protected List<Relate> find(RelateInfo info) {
		return this.relateDao.findRelates(info);
	}

	@Override
	protected RelateInfo changeModel(Relate data) {
		if(data == null) return null;
		RelateInfo info = new RelateInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getSubclass() != null){
				info.setClassId(data.getSubclass().getCode());
				info.setClassName(data.getSubclass().getName());
		}
		return info;
	}

	@Override
	protected Long total(RelateInfo info) {
		return this.relateDao.total(info);
	}

	@Override
	public RelateInfo update(RelateInfo info) {
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	
	
	@Override
	public void init(RelateInfo info) {
		if(info == null) return;
		logger.info("开始初始化课节...");
		List<Relate> data = this.dataServer.loadRelates(info.getClassId());
		if(data!=null &&data.size()>0)
		{
			this.relateDao.batchSave(data);
		}
		logger.info("初始化完成！");
	}
	
	@Override
	public void delete(RelateInfo info) {
		List<Relate> list = this.find(info);
		if(list==null || list.size()==0) return;
		for(Relate data:list){
			if(data==null) continue;
			this.relateDao.delete(data);
		}
	}
}
