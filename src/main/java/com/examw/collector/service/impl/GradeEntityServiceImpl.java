package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.IGradeEntityDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.domain.SubClass;
import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IGradeEntityService;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午4:34:44.
 */
public class GradeEntityServiceImpl extends
		BaseDataServiceImpl<GradeEntity, SubClassInfo> implements
		IGradeEntityService {
	private static Logger logger = Logger.getLogger(GradeEntityServiceImpl.class);
	private IGradeEntityDao gradeEntityDao;
	private ISubClassDao subClassDao;
	
	/**
	 * 设置 班级数据接口
	 * 
	 * @param subClassDao
	 * 
	 */
	public void setGradeEntityDao(IGradeEntityDao gradeEntityDao) {
		this.gradeEntityDao = gradeEntityDao;
	}
	
	/**
	 * 设置 环球班级数据接口
	 * @param subClassDao
	 * 
	 */
	public void setSubClassDao(ISubClassDao subClassDao) {
		this.subClassDao = subClassDao;
	}

	@Override
	protected List<GradeEntity> find(SubClassInfo info) {
		return this.gradeEntityDao.findSubClasses(info);
	}

	@Override
	protected SubClassInfo changeModel(GradeEntity data) {
		if (data == null)
			return null;
		SubClassInfo info = new SubClassInfo();
		BeanUtils.copyProperties(data, info);
		info.setCode(data.getId());
		// 设置科目
		if (data.getSubjectEntity() != null) {
			info.setSubjectId(data.getSubjectEntity().getId());
			info.setSubjectName(data.getSubjectEntity().getName());
		}
		return info;
	}

	@Override
	protected Long total(SubClassInfo info) {
		return this.gradeEntityDao.total(info);
	}

	@Override
	public SubClassInfo update(SubClassInfo info) {
		if (info == null)
			return null;
		if (StringUtils.isEmpty(info.getCode()))
			return null;
		GradeEntity data = this.gradeEntityDao.load(GradeEntity.class,
				info.getCode());
		// 只更新不添加
		if (data == null)
			return null;
		BeanUtils.copyProperties(info, data);
		if (!StringUtils.isEmpty(info.getSubjectId())) {
			SubjectEntity subject = new SubjectEntity();
			subject.setId(info.getSubjectId());
			data.setSubjectEntity(subject);
		}
		return info;
	}

	@Override
	public void delete(String[] ids) {
	}

	@Override
	public void init(SubClassInfo info) {
		if (info == null)
			return;
		logger.info("开始初始化班级...");
		List<SubClass> data = this.subClassDao.findSubClasses(info);
		if (data != null && data.size() > 0) {
			this.gradeEntityDao.batchSave(this.changeData(info, data));
		}
		logger.info("初始化完成！");
	}
	
	private List<GradeEntity> changeData(SubClassInfo info,List<SubClass> data){
		List<GradeEntity> list = new ArrayList<GradeEntity>();
		if(data == null || data.size()==0) return list;
		for(SubClass b:data){
			GradeEntity entity = new GradeEntity();
			BeanUtils.copyProperties(b, entity);
			entity.setId(b.getCode());
			SubjectEntity subject = new SubjectEntity();
			subject.setId(info.getSubjectId());
			entity.setSubjectEntity(subject);
			list.add(entity);
		}
		return list;
	}
}
