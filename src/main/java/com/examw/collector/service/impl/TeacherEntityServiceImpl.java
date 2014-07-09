package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ITeacherEntityDao;
import com.examw.collector.domain.SubClass;
import com.examw.collector.domain.local.TeacherEntity;
import com.examw.collector.model.TeacherInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.ITeacherEntityService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月9日 上午11:25:25.
 */
public class TeacherEntityServiceImpl  extends BaseDataServiceImpl<TeacherEntity, TeacherInfo> implements ITeacherEntityService{
	private static Logger logger = Logger.getLogger(TeacherEntityServiceImpl.class);
	
	private ITeacherEntityDao teacherEntityDao;
	
	private IDataServer dataServer;
	
	/**
	 * 设置 老师数据接口
	 * @param teacherEntityDao
	 * 
	 */
	public void setTeacherEntityDao(ITeacherEntityDao teacherEntityDao) {
		this.teacherEntityDao = teacherEntityDao;
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
	public void init(TeacherInfo info) {
		if(info == null || StringUtils.isEmpty(info.getCatalogId())) return;
		logger.info("初始化老师开始.........");
		List<SubClass> subClassList = this.dataServer.loadClasses(info.getCatalogId(), info.getSubjectId());
		Map<String,String> teacherMap = new HashMap<String,String>();
		if(subClassList!=null && subClassList.size()>0){
			for(SubClass sc: subClassList){
				if(!StringUtils.isEmpty(sc.getTeacherId())){
					teacherMap.put(sc.getTeacherId(), sc.getTeacherName());
				}
			}
		}
		if(teacherMap.size()>0){
			Set<String> keys = teacherMap.keySet();
			List<TeacherEntity> list = new ArrayList<TeacherEntity>();
			for(String id :keys){
				TeacherEntity t = this.teacherEntityDao.load(TeacherEntity.class, id);
				if(t!=null){
					if(StringUtils.isEmpty(t.getCatalogId())){
						t.setCatalogId(info.getCatalogId());
						list.add(t);
					}else if(!t.getCatalogId().contains(info.getCatalogId())){
						t.setCatalogId(t.getCatalogId()+","+info.getCatalogId());
						list.add(t);
					}
				}else{
					t = this.dataServer.loadTeacher(id);
					if(t!=null)
					{
						t.setCatalogId(info.getCatalogId());
						list.add(t);
					}
				}
			}
			if(list.size()>0)
			{
				this.teacherEntityDao.batchSave(list);
			}
		}
		logger.info("初始化老师结束.........");
	}

	@Override
	protected List<TeacherEntity> find(TeacherInfo info) {
		return this.teacherEntityDao.findTeachers(info);
	}

	@Override
	protected TeacherInfo changeModel(TeacherEntity data) {
		if(data == null) return null;
		TeacherInfo info = new TeacherInfo();
		BeanUtils.copyProperties(data, info);
		return info;
	}

	@Override
	protected Long total(TeacherInfo info) {
		return this.teacherEntityDao.total(info);
	}

	@Override
	public TeacherInfo update(TeacherInfo info) {
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	
}
