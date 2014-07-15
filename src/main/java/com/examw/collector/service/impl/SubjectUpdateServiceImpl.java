package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectUpdateService;
import com.examw.collector.support.JSONUtil;

/**
 * 科目数据更新服务接口实现类
 * @author fengwei.
 * @since 2014年7月9日 下午4:46:49.
 */
public class SubjectUpdateServiceImpl implements ISubjectUpdateService{
	
	private ISubjectDao subjectDao;
	private ICatalogDao catalogDao;
	private ISubjectEntityDao subjectEntityDao;
	private ICatalogEntityDao catalogEntityDao;
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
	 * 设置 远程科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置 本地科目数据接口
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}
	
	/**
	 * 设置 远程课程分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
	/**
	 * 设置 本地课程分类数据接口
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
	}
	@Override
	public void update(List<SubjectInfo> subjects,String account) {
		if(subjects == null ||subjects.size()==0) return;
		List<SubjectInfo> list = new ArrayList<SubjectInfo>();
		for(SubjectInfo info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				//本地副本
				Subject data1 = this.subjectDao.load(Subject.class, info.getCode());
				if(data1 != null){
					this.subjectDao.delete(data1);
					//TODO 是否连删
				}
				//实际数据
				SubjectEntity data2 = this.subjectEntityDao.load(SubjectEntity.class, info.getCode());
				if(data2 != null){
					this.subjectEntityDao.delete(data2);
					list.add(info);
					//TODO 是否连删
				}
				continue;
			}
			//本地副本
			Subject s = changeRemoteModel(info);
			if(s != null)
			{
				this.subjectDao.saveOrUpdate(s);
			}
			//实际数据
			SubjectEntity se = changeLocalModel(info);
			if(se !=null)
			{
				this.subjectEntityDao.saveOrUpdate(se);
				list.add(info);
			}
		}
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_SUBJECT);
		log.setName("更新科目数据");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent(JSONUtil.ObjectToJson(list));
		this.operateLogDao.save(log);
	}
	//本地科目数据模型转换
	private Subject changeRemoteModel(SubjectInfo info){
		if(info == null) return null;
		Subject data = new Subject();
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
			if(catalog==null) return null;
			data.setCatalog(catalog);
		}
		return data;
	}
	
	/*private void updateLocal(List<SubjectInfo> subjects) {
		if(subjects == null ||subjects.size()==0) return;
		List<SubjectInfo> list = new ArrayList<SubjectInfo>();
		for(SubjectInfo info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				SubjectEntity data = this.subjectEntityDao.load(SubjectEntity.class, info.getCode());
				if(data != null){
					this.subjectEntityDao.delete(data);
					list.add(info);
					//TODO 是否连删
				}
				continue;
			}
			SubjectEntity se = changeLocalModel(info);
			if(se !=null)
			{
				this.subjectEntityDao.saveOrUpdate(se);
				list.add(info);
			}
		}
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_SUBJECT);
		log.setName("更新科目数据(实际数据)");
		log.setAddTime(new Date());
		log.setContent(JSONUtil.ObjectToJson(list));
		this.operateLogDao.save(log);
	}*/
	//实际科目数据模型转换
	private SubjectEntity changeLocalModel(SubjectInfo info){
		if(info == null) return null;
		SubjectEntity data = new SubjectEntity();
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			CatalogEntity catalog = this.catalogEntityDao.find(info.getCatalogId());
			if(catalog==null) return null;
			data.setCatalogEntity(catalog);
		}
		return data;
	}
}
