package com.examw.collector.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.Subject;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectService;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午3:55:20.
 */
public class SubjectServiceImpl extends BaseDataServiceImpl<Subject, SubjectInfo> implements ISubjectService{
	//private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private ISubjectDao subjectDao;
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	@Override
	protected List<Subject> find(SubjectInfo info) {
		return this.subjectDao.findSubjects(info);
	}

	@Override
	protected SubjectInfo changeModel(Subject data) {
		if(data == null) return null;
		SubjectInfo info = new SubjectInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getCatalog() != null){
				info.setCatalogId(data.getCatalog().getCode());
				info.setCatalogName(data.getCatalog().getName());
		}
		return info;
	}

	@Override
	protected Long total(SubjectInfo info) {
		return this.subjectDao.total(info);
	}

	@Override
	public SubjectInfo update(SubjectInfo info) {
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	
}
