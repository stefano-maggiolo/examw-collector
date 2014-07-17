package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IGradeEntityDao;
import com.examw.collector.dao.IListenEntityDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.IRelateDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Relate;
import com.examw.collector.domain.SubClass;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.domain.local.ListenEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IGradeUpdateService;
import com.examw.collector.support.JSONUtil;

/**
 * 班级数据更新服务接口实现类
 * @author fengwei.
 * @since 2014年7月9日 下午5:01:58.
 */
public class GradeUpdateServiceImpl implements IGradeUpdateService{
	private ISubClassDao subClassDao;
	private ISubjectDao subjectDao;
	private IGradeEntityDao gradeEntityDao;
	private ISubjectEntityDao subjectEntityDao;
	private IListenEntityDao listenEntityDao;
	private IDataServer dataServer;
	private IRelateDao relateDao;
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
	 * 设置 本地科目数据接口
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}
	
	/**
	 * 设置 本地课节数据接口
	 * @param listenEntityDao
	 * 
	 */
	public void setListenEntityDao(IListenEntityDao listenEntityDao) {
		this.listenEntityDao = listenEntityDao;
	}

	/**
	 * 设置 远程数据获取接口
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
	}
	/**
	 * 设置 远程课节数据接口
	 * @param relateDao
	 * 
	 */
	public void setRelateDao(IRelateDao relateDao) {
		this.relateDao = relateDao;
	}

	@Override
	public List<SubClassInfo> update(List<SubClassInfo> subClasses,String account) {
		if(subClasses == null ||subClasses.size()==0) return null;
		List<SubClassInfo> list = new ArrayList<SubClassInfo>();
		for(SubClassInfo info:subClasses){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				//本地副本
				SubClass data1 = this.subClassDao.load(SubClass.class, info.getCode());
				GradeEntity data2 = this.gradeEntityDao.load(GradeEntity.class, info.getCode());
				if(data1 != null && data2!=null){
					//要先删课节
					this.relateDao.delete(data1.getCode());
					this.subClassDao.delete(data1);
					//TODO 是否连删
				}
				//实际数据
				if(data2 != null){
					//先删课节
					this.listenEntityDao.delete(data2.getId());
					this.gradeEntityDao.delete(data2);
					list.add(info);
					//TODO 是否连删
				}
				continue;
			}
			SubClass sc = this.changeRemoteModel(info);
			GradeEntity g = changeLocalModel(info);
			if(sc!=null && g!=null)
			{
				this.subClassDao.saveOrUpdate(changeRemoteModel(info));
			}
			if(g!=null)
			{
				this.gradeEntityDao.saveOrUpdate(g);
				//删除原来班级带的课节地址,重新插入新的
				this.deleteOldAndInsertNewListen(info.getCode());
				list.add(info);
			}
		}
		// 添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_GRADE);
		log.setName("更新班级数据");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent(JSONUtil.ObjectToJson(list));
		this.operateLogDao.save(log);
		return list;
	}
	/**
	 * 本地数据模型转换
	 * @param info
	 * @return
	 */
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
	
	/*private void updateLocal(List<SubClassInfo> subClasses) {
		if(subClasses == null ||subClasses.size()==0) return;
		List<SubClassInfo> list = new ArrayList<SubClassInfo>();
		for(SubClassInfo info:subClasses){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				GradeEntity data = this.gradeEntityDao.load(GradeEntity.class, info.getCode());
				if(data != null){
					//先删课节
					this.listenEntityDao.delete(data.getId());
					this.gradeEntityDao.delete(data);
					list.add(info);
					//TODO 是否连删
				}
				continue;
			}
			GradeEntity g = changeLocalModel(info);
			if(g!=null)
			{
				this.gradeEntityDao.saveOrUpdate(g);
				//删除原来班级带的课节地址,重新插入新的
				this.deleteOldAndInsertNewListen(info.getCode());
				list.add(info);
			}
		}
		// 添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_GRADE);
		log.setName("更新班级数据(实际数据)");
		log.setAddTime(new Date());
		log.setContent(JSONUtil.ObjectToJson(list));
		this.operateLogDao.save(log);
	}*/
	
	/**
	 * 实际班级数据模型转换
	 * @param info
	 * @return
	 */
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
	/**
	 * 删除实际班级数据,然后插入新进数据
	 * @param gradeId
	 */
	private void deleteOldAndInsertNewListen(String gradeId){
		//删除
		this.listenEntityDao.delete(gradeId);
		//插入
		List<Relate> list = this.dataServer.loadRelates(gradeId);
		List<ListenEntity> data = new ArrayList<ListenEntity>();
		if(list!=null && list.size()>0){
			for(Relate r:list){
				ListenEntity l = this.changeListenModel(r);
				if(l!=null)
					data.add(l);
			}
		}
		if(data.size()>0){
			this.listenEntityDao.batchSave(data);
		}
	}
	private ListenEntity changeListenModel(Relate relate){
		if(relate == null) return null;
		ListenEntity data = new ListenEntity();
		BeanUtils.copyProperties(relate, data);
		data.setId(relate.getNum().toString());
		if(relate.getSubclass()==null) return null;
		GradeEntity grade = new GradeEntity();
		grade.setId(relate.getSubclass().getCode());
		data.setGrade(grade);
		return data;
	}
}
