package com.examw.collector.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectUpdateService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月9日 下午4:46:49.
 */
public class SubjectUpdateServiceImpl implements ISubjectUpdateService{
	
	private ISubjectDao subjectDao;
	private ICatalogDao catalogDao;
	private ISubjectEntityDao subjectEntityDao;
	private ICatalogEntityDao catalogEntityDao;
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
	public void update(List<SubjectInfo> subjects) {
		updateRemote(subjects);
		updateLocal(subjects);
	}
	
	private void updateRemote(List<SubjectInfo> subjects){
		if(subjects == null ||subjects.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(SubjectInfo info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
			}
			this.subjectDao.saveOrUpdate(changeRemoteModel(info));
		}
		if(buf.length()>0)
		{
			String[] ids = buf.toString().split(",");
			if(ids == null ||ids.length==0) return;
			for(String id:ids){
				Subject data = this.subjectDao.load(Subject.class, id);
				if(data != null){
					this.subjectDao.delete(data);
					//TODO 是否连删
				}
			}
		}
	}
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
	
	private void updateLocal(List<SubjectInfo> subjects) {
		if(subjects == null ||subjects.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(SubjectInfo info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
			}
			this.subjectEntityDao.saveOrUpdate(changeLocalModel(info));
		}
		if(buf.length()>0)
		{
			String[] ids = buf.toString().split(",");
			if(ids == null ||ids.length==0) return;
			for(String id:ids){
				SubjectEntity data = this.subjectEntityDao.load(SubjectEntity.class, id);
				if(data != null){
					this.subjectEntityDao.delete(data);
					//TODO 是否连删
				}
			}
		}
	}
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
