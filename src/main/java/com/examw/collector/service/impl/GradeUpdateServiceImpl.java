package com.examw.collector.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IGradeEntityDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.SubClass;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IGradeUpdateService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月9日 下午5:01:58.
 */
public class GradeUpdateServiceImpl implements IGradeUpdateService{
	private ISubClassDao subClassDao;
	private ISubjectDao subjectDao;
	private IGradeEntityDao gradeEntityDao;
	private ISubjectEntityDao subjectEntityDao;
	
	/**
	 * 设置 远程班级数据接口
	 * @param subClassDao
	 * 
	 */
	public void setSubClassDao(ISubClassDao subClassDao) {
		this.subClassDao = subClassDao;
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
	 * 设置 本地班级数据接口
	 * @param gradeEntityDao
	 * 
	 */
	public void setGradeEntityDao(IGradeEntityDao gradeEntityDao) {
		this.gradeEntityDao = gradeEntityDao;
	}

	/**
	 * 设置 本地科目科目数据接口
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}

	@Override
	public void update(List<SubClassInfo> subClasses) {
		updateRemote(subClasses);
		updateLocal(subClasses);
	}
	
	private void updateRemote(List<SubClassInfo> subClasses){
		if(subClasses == null ||subClasses.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(SubClassInfo info:subClasses){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
			}
			this.subClassDao.saveOrUpdate(changeRemoteModel(info));
		}
		if(buf.length()>0)
		{
			String[] ids = buf.toString().split(",");
			if(ids == null ||ids.length==0) return;
			for(String id:ids){
				SubClass data = this.subClassDao.load(SubClass.class, id);
				if(data != null){
					this.subClassDao.delete(data);
					//TODO 是否连删
				}
			}
		}
	}
	private SubClass changeRemoteModel(SubClassInfo info){
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
	
	private void updateLocal(List<SubClassInfo> subClasses) {
		if(subClasses == null ||subClasses.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(SubClassInfo info:subClasses){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
			}
			this.gradeEntityDao.saveOrUpdate(changeLocalModel(info));
		}
		if(buf.length()>0)
		{
			String[] ids = buf.toString().split(",");
			if(ids == null ||ids.length==0) return;
			for(String id:ids){
				GradeEntity data = this.gradeEntityDao.load(GradeEntity.class, id);
				if(data != null){
					this.gradeEntityDao.delete(data);
					//TODO 是否连删
				}
			}
		}
		
	}
	private GradeEntity changeLocalModel(SubClassInfo info){
		if(info == null) return null;
		GradeEntity data = new GradeEntity();
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if(StringUtils.isEmpty(info.getSubjectId())){
			return null;
		}else{
			SubjectEntity subject = this.subjectEntityDao.load(SubjectEntity.class,info.getSubjectId());
			if(subject==null) return null;
			data.setSubjectEntity(subject);
		}
		return data;
	}
}
