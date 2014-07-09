package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IListenEntityDao;
import com.examw.collector.dao.IRelateDao;
import com.examw.collector.domain.Relate;
import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.domain.local.ListenEntity;
import com.examw.collector.model.RelateInfo;
import com.examw.collector.service.IListenEntityService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:24:19.
 */
public class ListenEntityServiceImpl extends BaseDataServiceImpl<ListenEntity, RelateInfo> implements IListenEntityService{
	private static Logger logger = Logger.getLogger(ListenEntityServiceImpl.class);
	private IListenEntityDao listenEntityDao;
//	private IDataServer dataServer;
	private IRelateDao relateDao;
	/**
	 * 设置 课节数据接口
	 * @param relateDao
	 * 
	 */
	public void setListenEntityDao(IListenEntityDao listenEntityDao) {
		this.listenEntityDao = listenEntityDao;
	}
	
	/**
	 * 设置 设置远程课节数据接口
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
	
	
	@Override
	public void init(RelateInfo info) {
		if(info == null) return;
		logger.info("开始初始化课节...");
//		List<Relate> data = this.dataServer.loadRelates(info.getClassId());
		List<Relate> data = this.relateDao.findRelates(info);
		if(data!=null &&data.size()>0)
		{
			this.listenEntityDao.batchSave(changeData(data));
		}
		logger.info("初始化完成！");
	}
	private List<ListenEntity> changeData(List<Relate> list){
		List<ListenEntity> data = new ArrayList<ListenEntity>();
		if(list!=null&&list.size()>0){
			for(Relate r:list){
				ListenEntity listen = new ListenEntity();
				BeanUtils.copyProperties(r, listen,new String[]{"id"});
				listen.setId(r.getNum().toString());
				if(r.getSubclass()!=null){
					GradeEntity g = new GradeEntity();
					g.setId(r.getSubclass().getCode());
					listen.setGrade(g);
				}
				data.add(listen);
			}
		}
		return data;
	}
	
	@Override
	public void delete(RelateInfo info) {
		logger.info("开始删除课节...");
		List<ListenEntity> list = this.listenEntityDao.findRelates(info);
		if(list==null || list.size()==0) return;
		for(ListenEntity data:list){
			if(data==null) continue;
			this.listenEntityDao.delete(data);
		}
		logger.info("删除完成！");
	}
}
