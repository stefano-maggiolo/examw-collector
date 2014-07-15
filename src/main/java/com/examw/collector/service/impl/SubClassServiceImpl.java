package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IAdVideoDao;
import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.AdVideo;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.SubClass;
import com.examw.collector.domain.Subject;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.ISubClassService;
import com.examw.model.DataGrid;

/**
 * 班级服务接口实现类
 * @author fengwei.
 * @since 2014年6月30日 下午4:34:44.
 */
public class SubClassServiceImpl extends BaseDataServiceImpl<SubClass, SubClassInfo> implements ISubClassService{
	private static Logger logger = Logger.getLogger(SubClassServiceImpl.class);
	private ISubClassDao subClassDao;
	private IAdVideoDao adVideoDao;
	private IDataServer dataServer;
	private ISubjectDao subjectDao;
	private ICatalogDao catalogDao;
	private IOperateLogDao operateLogDao;
	/**
	 * 设置操作日志数据接口
	 * @param operateLogDao
	 * 
	 */
	public void setOperateLogDao(IOperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}
	/**
	 * 设置 课程分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
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
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
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
		if(ids == null ||ids.length==0) return;
		for(String id:ids){
			SubClass data = this.subClassDao.load(SubClass.class, id);
			if(data != null){
				this.subClassDao.delete(data);
				//TODO 是否连删
			}
		}
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
		logger.info("AdVideo的个数是:"+data.size());
		return data;
	}
	
	@Override
	public DataGrid<SubClassInfo> dataGridUpdate(SubClassInfo info,String account) {
		if(StringUtils.isEmpty(info.getCatalogId()))
			return null;
		List<SubClass> list = this.findChangedSubClass(info,account);
		DataGrid<SubClassInfo> grid = new DataGrid<SubClassInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal((long) list.size());
		return grid;
		
		
	}
	private List<SubClass> findChangedSubClass(SubClassInfo info,String account){
		Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
		//Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
		List<SubClass> data = this.dataServer.loadClasses(info.getCatalogId(), info.getSubjectId());
		List<SubClass> add = new ArrayList<SubClass>();
		StringBuffer existIds = new StringBuffer();
		for(SubClass s:data){
			if(!StringUtils.isEmpty(s.getCode())) existIds.append(s.getCode()).append(",");
			SubClass local_s = this.subClassDao.load(SubClass.class, s.getCode());
			if(local_s == null){
				s.setStatus("新增");
				add.add(s);
			}else if(s.equals(local_s)){
				continue;
			}else{
				s.setStatus("新的");
				//local_s.setStatus("旧的");
				add.add(s);
				//add.add(local_s);
			}
		}
		if(existIds.length()>0)
		{
			existIds.append("0");
			List<SubClass> deleteList = this.subClassDao.findDeleteSubClasss(existIds.toString(),info);
			if(deleteList!=null && deleteList.size()>0)
			{
				for(SubClass s:deleteList){
					s.setStatus("被删");
				}
				add.addAll(deleteList);
			}
		}
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_CHECK_UPDATE);
		log.setName("检测班级数据更新");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent("检测 "+catalog.getName()+"("+catalog.getCode()+")的班级数据更新 ");
		this.operateLogDao.save(log);
		return add;
	}
	@Override
	public void update(List<SubClassInfo> subClasses) {
		if(subClasses == null ||subClasses.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(SubClassInfo info:subClasses){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
			}
			this.subClassDao.saveOrUpdate(changeModel(info));
		}
		if(buf.length()>0)
			this.delete(buf.toString().split(","));
	}
	private SubClass changeModel(SubClassInfo info){
		if(info == null) return null;
		SubClass data = new SubClass();
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getSubjectId())){
			return null;
		}else{
			Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
			if(subject==null) return null;
			data.setSubject(subject);
			data.setCatalog(subject.getCatalog());
		}
		return data;
	}
}
