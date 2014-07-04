package com.examw.collector.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IListenEntityDao;
import com.examw.collector.domain.local.ListenEntity;
import com.examw.collector.model.RelateInfo;
import com.examw.collector.service.IListenEntityService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:24:19.
 */
public class ListenEntityServiceImpl extends BaseDataServiceImpl<ListenEntity, RelateInfo> implements IListenEntityService{
//	private static Logger logger = Logger.getLogger(ListenEntityServiceImpl.class);
	private IListenEntityDao listenEntityDao;
//	private IDataServer dataServer;
	
	/**
	 * 设置 课节数据接口
	 * @param relateDao
	 * 
	 */
	public void setListenEntityDao(IListenEntityDao listenEntityDao) {
		this.listenEntityDao = listenEntityDao;
	}
	
	/**
	 * 设置  远程数据接口
	 * @param dataServer
	 * 
	 */
//	public void setDataServer(IDataServer dataServer) {
//		this.dataServer = dataServer;
//	}

	@Override
	protected List<ListenEntity> find(RelateInfo info) {
		return this.listenEntityDao.findRelates(info);
	}

	@Override
	protected RelateInfo changeModel(ListenEntity data) {
		if(data == null) return null;
		RelateInfo info = new RelateInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getGrade() != null){
				info.setClassId(data.getGrade().getId());
				info.setClassName(data.getGrade().getName());
		}
		return info;
	}

	@Override
	protected Long total(RelateInfo info) {
		return this.listenEntityDao.total(info);
	}

	@Override
	public RelateInfo update(RelateInfo info) {
//		if(info == null) return null;
//		ListenEntity data = StringUtils.isEmpty(info.getId())
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	
	
	/*@Override
	public void init(RelateInfo info) {
		if(info == null) return;
		logger.info("开始初始化课节...");
		List<Relate> data = this.dataServer.loadRelates(info.getClassId());
		if(data!=null &&data.size()>0)
		{
			this.relateDao.batchSave(data);
		}
		logger.info("初始化完成！");
	}*/
}
