package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.IGradeEntityDao;
import com.examw.collector.dao.IListenEntityDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.IPackDao;
import com.examw.collector.dao.IPackageEntityDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.dao.IUpdateRecordDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.UpdateRecord;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.GradeEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.ISubjectUpdateService;
import com.examw.collector.support.JSONUtil;
import com.examw.model.DataGrid;

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
	private IDataServer dataServer;
	private IOperateLogDao operateLogDao;
	private ISubClassDao subClassDao;
	private IGradeEntityDao gradeEntityDao;
	private IListenEntityDao listenEntityDao;
	private IPackageEntityDao packageEntityDao;
	private IPackDao packDao;
	private IUpdateRecordDao updateRecordDao;
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
	
	/**
	 * 设置 设置远程数据接口
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
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
	 * 设置 实际班级数据接口
	 * @param gradeEntityDao
	 * 
	 */
	public void setGradeEntityDao(IGradeEntityDao gradeEntityDao) {
		this.gradeEntityDao = gradeEntityDao;
	}
	/**
	 * 设置 试听数据接口
	 * @param listenEntityDao
	 * 
	 */
	public void setListenEntityDao(IListenEntityDao listenEntityDao) {
		this.listenEntityDao = listenEntityDao;
	}
	/**
	 * 设置 实际套餐数据接口
	 * @param packEntityDao
	 * 
	 */
	public void setPackageEntityDao(IPackageEntityDao packageEntityDao) {
		this.packageEntityDao = packageEntityDao;
	}
	/**
	 * 设置 套餐数据接口
	 * @param packDao
	 * 
	 */
	public void setPackDao(IPackDao packDao) {
		this.packDao = packDao;
	}
	
	/**
	 * 设置 更新记录数据接口
	 * @param updateRecordDao
	 * 
	 */
	public void setUpdateRecordDao(IUpdateRecordDao updateRecordDao) {
		this.updateRecordDao = updateRecordDao;
	}
	@Override
	public List<SubjectInfo> update(List<SubjectInfo> subjects,String account) {
		if(subjects == null ||subjects.size()==0) return null;
		List<SubjectInfo> list = new ArrayList<SubjectInfo>();
		for(SubjectInfo info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				//本地副本
				Subject data1 = this.subjectDao.load(Subject.class, info.getCode());
				SubjectEntity data2 = this.subjectEntityDao.load(SubjectEntity.class, info.getCode());
				if(data1 != null){
					this.subjectDao.delete(data1);
					//TODO 是否连删
				}
				//实际数据
				if(data2 != null){
					this.subjectEntityDao.delete(data2);
					list.add(info);
					//TODO 是否连删
				}
				continue;
			}
			//本地副本
			Subject s = changeRemoteModel(info);
			SubjectEntity se = changeLocalModel(info);
			if(s != null)
			{
				this.subjectDao.saveOrUpdate(s);
			}
			//实际数据
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
		return list;
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
	//实际科目数据模型转换
	private SubjectEntity changeLocalModel(SubjectInfo info){
		if(info == null) return null;
		SubjectEntity data = new SubjectEntity();
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			CatalogEntity catalog = this.catalogEntityDao.find(info.getCatalogId());
			if(catalog==null) return null;
			data.setCatalogEntity(catalog);
		}
		return data;
	}
	//本地科目数据转换为实际科目数据
	private SubjectEntity changeModel(Subject info)
	{
		if(info == null) return null;
		SubjectEntity data = new SubjectEntity();	//id,name,fudao,catalogId
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if(info.getCatalog()==null || StringUtils.isEmpty(info.getCatalog().getCode())){
			return null;
		}else{
			CatalogEntity catalog = this.catalogEntityDao.find(info.getCatalog().getCode());
			if(catalog==null) return null;
			data.setCatalogEntity(catalog);
		}
		return data;
	}
	/**
	 * 返回更新以后的结果
	 */
	@Override
	public DataGrid<SubjectInfo> dataGridUpdate(String account) {
		List<SubjectInfo> list = this.update(account);
		DataGrid<SubjectInfo> grid = new DataGrid<SubjectInfo>();
		grid.setRows(list);
		grid.setTotal((long) list.size());
		return grid;
	}
	/**
	 * 查找需要进行更新的科目
	 * @param account
	 * @return
	 */
	private List<Subject> findChangedSubject(){
		List<Catalog> data = this.dataServer.loadCatalogs();
		//获取需要进行比对的所有科目
		List<Subject> subjects = getSubjects(data);
		if(subjects.size() == 0) return subjects;
		List<Subject> add = new ArrayList<Subject>();
		StringBuffer existIds = new StringBuffer();
		//比对科目
		for(Subject s:subjects){
			if(!StringUtils.isEmpty(s.getCode())) existIds.append("'"+s.getCode()+"'").append(",");
			Subject local_s = this.subjectDao.load(Subject.class, s.getCode());
			if(local_s == null){
				s.setStatus("新增");
				s.setUpdateInfo("<span style='color:blue'>[新增]</span>"+s.toString());
				add.add(s);
			}else if(s.equals(local_s)){
				continue;
			}else{
				s.setStatus("新的");
				//local_s.setStatus("旧的");
				s.setUpdateInfo("<span style='color:red'>[更新]</span>"+s.getUpdateInfo());
				BeanUtils.copyProperties(s, local_s);	//已经存在的,必须用原有的数据进行更新,不然会出错
				add.add(local_s);
				//add.add(local_s);
			}
		}
		if(existIds.length()>0)
		{
			existIds.append("'0'");
			List<Subject> deleteList = this.subjectDao.findDeleteSubjects(existIds.toString(),getCodes(this.catalogEntityDao.findAllWithCode()));
			if(deleteList!=null && deleteList.size()>0)
			{
				for(Subject s:deleteList){
					if(StringUtils.isEmpty(s.getAdd()))	//如果不是自己加上的,没有就没有
					{
						s.setStatus("被删");
						s.setUpdateInfo(s.toString());
					}
				}
				add.addAll(deleteList);
			}
		}
		return add;
	}
	/**
	 * 获取考试科目
	 * @param catalogs
	 * @return
	 */
	private List<Subject> getSubjects(List<Catalog> catalogs){
		List<Subject> data = new ArrayList<Subject>();
		List<CatalogEntity> needList = this.catalogEntityDao.findAllWithCode();
		for(Catalog c:catalogs){
			if(c.getChildren()!=null && c.getChildren().size()>0){
				Set<Catalog> children = c.getChildren();
				for(Catalog child : children){	
					for(CatalogEntity entity:needList)
					{
						//只比较子类的code
						if((","+entity.getCode()+",").contains(","+child.getCode()+","))
						{
							if(child.getSubjects()!=null && child.getSubjects().size()>0){
								data.addAll(child.getSubjects());
							}
							break;
						}
					}
				}
			}
		}
		return data;
	}
	
	/**
	 * 直接更新
	 * @param account
	 * @return
	 */
	public List<SubjectInfo> update(String account) {
		//找出需要查找并且有变化的科目集合
		List<Subject> subjects = this.findChangedSubject();
		if(subjects == null ||subjects.size()==0) return new ArrayList<SubjectInfo>();
		for(Subject info:subjects){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				//本地副本
				Subject data1 = this.subjectDao.load(Subject.class, info.getCode());
				SubjectEntity data2 = this.subjectEntityDao.load(SubjectEntity.class, info.getCode());
				if(data1 != null){
					this.casecadeDelete(data1);	//级联删除
					this.subjectDao.delete(data1);
					//TODO 是否连删
				}
				if(data2 != null)
				{
					this.casecadeDelete(data2);	//级联删除
					this.subjectEntityDao.delete(data2);
				}else{
					info.setUpdateInfo("<span style='color:purple'>删除失败</span>"+info.getUpdateInfo());
				}
				continue;
			}
			//本地副本
			Subject s = judgeDataSafe(info);
			SubjectEntity se = changeModel(info);
			if(s != null)
			{
				this.subjectDao.saveOrUpdate(s);
			}
			//实际数据
			if(se !=null)
			{
				this.subjectEntityDao.saveOrUpdate(se);
				info.setStatus("更新成功");
			}else{
				info.setUpdateInfo("<span style='color:purple'>插入或更新失败</span>"+info.getUpdateInfo());
			}
		}
		List<SubjectInfo> result = this.changeModel(subjects);
		addToUpdateRecord(subjects);
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_SUBJECT);
		log.setName("更新科目数据");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent(JSONUtil.ObjectToJson(result));
		this.operateLogDao.save(log);
		return result;
	}
	/**
	 * 判断数据是否安全
	 * @param info
	 * @return
	 */
	private Subject judgeDataSafe(Subject info)
	{
		if(info == null) return null;
		if(info.getCatalog()==null || StringUtils.isEmpty(info.getCatalog().getCode())){
			return null;
		}else{
			Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalog().getCode());
			if(catalog==null) return null;
			info.setCatalog(catalog);
		}
		return info;
	}
	private List<SubjectInfo> changeModel(List<Subject> list) {
		List<SubjectInfo> results = new ArrayList<>();
		if(list != null && list.size() > 0){
			for(Subject data : list){
				if(data == null || StringUtils.isEmpty(data.getStatus())) continue;	//没有状态的不显示
				SubjectInfo info = this.change2InfoModel(data);
				if(info != null){
					results.add(info);
				}
			}
		}
		return results;
	}
	private SubjectInfo change2InfoModel(Subject data){
		if(data == null) return null;
		SubjectInfo info = new SubjectInfo();
		BeanUtils.copyProperties(data, info);
		if(data.getCatalog() != null){
				info.setCatalogId(data.getCatalog().getCode());
				info.setCatalogName(data.getCatalog().getName());
		}
		return info;
	}
	
	private String getCodes(List<CatalogEntity> list){
		StringBuilder s = new StringBuilder();
		for(CatalogEntity entity:list)
		{
			s.append("'"+entity.getCode()+"'").append(",");
		}
		if(s.length()>0){
			s.append("'0'");
		}
		return s.toString();
	}
	/**
	 * 级联删除科目下的班级,套餐
	 * @param info
	 */
	private void casecadeDelete(Subject info){
		//删除副本班级[副本没有试听,可以直接删]
		this.subClassDao.delete(info.getCode());
		//删除副本的套餐[没有关联数据直接删]
		this.packDao.delete(info.getCode());
	}
	/**
	 * 级联删除科目下的班级[带试听],套餐数据
	 * @param info
	 */
	private void casecadeDelete(SubjectEntity info){
		final String subjectId = info.getId();
		List<GradeEntity> gradeList = this.gradeEntityDao.findSubClasses(new SubClassInfo(){
			private static final long serialVersionUID = 1L;
			@Override
			public String getSubjectId() {
				return subjectId;
			}
		});
		if(gradeList!=null&&gradeList.size()>0)
		{
			for(GradeEntity e:gradeList)
			{
				if(e == null) continue;
				this.listenEntityDao.delete(e.getId());
				this.gradeEntityDao.delete(e);
			}
		}
		this.packageEntityDao.delete(info.getId());
	}
	/**
	 * 加到更新记录中
	 * @param list
	 */
	private void addToUpdateRecord(List<Subject> list){
		if(list.size() == 0) return;
		for(Subject info:list){
			UpdateRecord data = new UpdateRecord();
			data = new UpdateRecord(UUID.randomUUID().toString(),info.getCode(),
					info.getStatus(),info.getUpdateInfo(),UpdateRecord.TYPE_UPDATE_SUBJECT,"更新成功".equals(info.getStatus())?"更新成功":"更新失败",new Date());
			this.updateRecordDao.save(data);
		}
	}
}
