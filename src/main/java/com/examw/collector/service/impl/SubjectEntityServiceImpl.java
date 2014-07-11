package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectEntityService;

/**
 * 科目服务接口实现类
 * @author fengwei.
 * @since 2014年6月30日 下午3:55:20.
 */
public class SubjectEntityServiceImpl extends BaseDataServiceImpl<SubjectEntity, SubjectInfo> implements ISubjectEntityService{
	private static Logger logger = Logger.getLogger(SubjectEntityServiceImpl.class);
	private ISubjectEntityDao subjectEntityDao;
	private ISubjectDao subjectDao;
	private ICatalogEntityDao catalogEntityDao;
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}

	/**
	 * 设置 环球的科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	
	/**
	 * 设置 课程分类数据接口
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
	}

	@Override
	protected List<SubjectEntity> find(SubjectInfo info) {
		return this.subjectEntityDao.findSubjects(info);
	}

	@Override
	protected SubjectInfo changeModel(SubjectEntity data) {
		if(data == null) return null;
		SubjectInfo info = new SubjectInfo();
		BeanUtils.copyProperties(data, info);
		info.setCode(data.getId());
		if(data.getCatalogEntity() != null){
				info.setCatalogId(data.getCatalogEntity().getCode());
				info.setCatalogName(data.getCatalogEntity().getCname());
		}
		return info;
	}

	@Override
	protected Long total(SubjectInfo info) {
		return this.subjectEntityDao.total(info);
	}

	@Override
	public SubjectInfo update(SubjectInfo info) {
		if(info == null) return null;
		if(StringUtils.isEmpty(info.getCode())) return null;
		SubjectEntity data = this.subjectEntityDao.load(SubjectEntity.class, info.getCode());
		//只更新不添加
		if(data == null) return null;
		if(!StringUtils.isEmpty(info.getCatalogId())){
			CatalogEntity catalog = new CatalogEntity();
			catalog.setId(info.getCode());
			data.setCatalogEntity(catalog);
		}
		data.setName(info.getName());
		return info;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null ||ids.length==0) return;
		for(String id:ids){
			SubjectEntity data = this.subjectEntityDao.load(SubjectEntity.class, id);
			if(data != null){
				this.subjectEntityDao.delete(data);
				//TODO 是否连删
			}
		}
	}
	
	@Override
	public void init(SubjectInfo info) {
		logger.info("开始初始化课程分类...");
		List<Subject> data = this.subjectDao.findSubjects(info);
		if(data!=null &&data.size()>0)
		{
			this.subjectEntityDao.batchSave(this.changeData(info,data));
		}
		logger.info("初始化完成！");
	}
	private List<SubjectEntity> changeData(SubjectInfo info,List<Subject> data){
		List<SubjectEntity> list = new ArrayList<SubjectEntity>();
		if(data == null || data.size()==0) return list;
		for(Subject b:data){
			SubjectEntity entity = new SubjectEntity();
			BeanUtils.copyProperties(b, entity);
			entity.setId(b.getCode());
			if(b.getCatalog()!=null){
				CatalogEntity ce = new CatalogEntity();
				ce.setId(info.getCatalogId());
				ce.setCode(info.getCatalogId());
				entity.setCatalogEntity(ce);
			}
			list.add(entity);
		}
		return list;
	}
	

	@Override
	public void update(List<SubjectInfo> subjects) {
		if(subjects == null ||subjects.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(SubjectInfo info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
				continue;
			}
			this.subjectEntityDao.saveOrUpdate(changeModel(info));
		}
		if(buf.length()>0)
			this.delete(buf.toString().split(","));
	}
	private SubjectEntity changeModel(SubjectInfo info){
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
