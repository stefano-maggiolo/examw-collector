package com.examw.collector.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.IPackDao;
import com.examw.collector.dao.IPackageEntityDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.PackageEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IPackageUpdateService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月9日 下午5:12:19.
 */
public class PackageUpdateServiceImpl implements IPackageUpdateService{
	private IPackDao packDao;
	private ISubjectDao subjectDao;
	private ICatalogDao catalogDao;
	private IPackageEntityDao packageEntityDao;
	private ISubjectEntityDao subjectEntityDao;
	private ICatalogEntityDao catalogEntityDao;
	
	/**
	 * 设置 远程套餐数据接口
	 * @param packDao
	 * 
	 */
	public void setPackDao(IPackDao packDao) {
		this.packDao = packDao;
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
	 * 设置 远程分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	/**
	 * 设置 本地套餐数据接口
	 * @param packageEntityDao
	 * 
	 */
	public void setPackageEntityDao(IPackageEntityDao packageEntityDao) {
		this.packageEntityDao = packageEntityDao;
	}

	/**
	 * 设置  本地科目数据接口
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}

	/**
	 * 设置  本地分类数据接口
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
	}

	@Override
	public void update(List<PackInfo> packs) {
		updateRemote(packs);
		
		updateLocal(packs);
	}
	
	private void updateRemote(List<PackInfo> packs){
		if(packs == null ||packs.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(PackInfo info:packs){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
				continue;
			}
			this.packDao.saveOrUpdate(changeRemoteModel(info));
		}
		if(buf.length()>0)
		{
			String[] ids = buf.toString().split(",");
			if(ids == null ||ids.length==0) return;
			for(String id:ids){
				Pack data = this.packDao.load(Pack.class, id);
				if(data != null){
					this.packDao.delete(data);
				}
			}
		}
	}
	
	private Pack changeRemoteModel(PackInfo info)
	{
		if(info == null) return null;
		Pack data = new Pack();
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			if(StringUtils.isEmpty(info.getSubjectId())){
				Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
				if(catalog == null) return null;
				data.setCatalog(catalog);
			}else{
				Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
				if(subject==null) return null;
				data.setSubject(subject);
				data.setCatalog(subject.getCatalog());
			}
		}
		return data;
	}
	
	private void updateLocal(List<PackInfo> packs){
		if(packs == null ||packs.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(PackInfo info:packs){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
			}
			this.packageEntityDao.saveOrUpdate(changeLocalModel(info));
		}
		if(buf.length()>0)
		{
			String[] ids = buf.toString().split(",");
			if(ids == null ||ids.length==0) return;
			for(String id:ids){
				PackageEntity data = this.packageEntityDao.load(PackageEntity.class, id);
				if(data != null){
					this.packageEntityDao.delete(data);
				}
			}
		}
	}
	
	private PackageEntity changeLocalModel(PackInfo info)
	{
		if(info == null) return null;
		PackageEntity data = new PackageEntity();
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			if(StringUtils.isEmpty(info.getSubjectId()))
			{
				CatalogEntity catalog = this.catalogEntityDao.find(info.getCatalogId());
				if(catalog == null) return null;
				data.setCatalogEntity(catalog);
			}else{
				SubjectEntity subject = this.subjectEntityDao.load(SubjectEntity.class,info.getSubjectId());
				if(subject==null) return null;
				data.setSubjectEntity(subject);
				data.setCatalogEntity(subject.getCatalogEntity());
			}
		}
		return data;
	}
}
