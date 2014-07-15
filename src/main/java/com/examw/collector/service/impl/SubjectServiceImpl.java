package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Subject;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.ISubjectService;
import com.examw.model.DataGrid;

/**
 * 科目服务接口实现类
 * @author fengwei.
 * @since 2014年6月30日 下午3:55:20.
 */
public class SubjectServiceImpl extends BaseDataServiceImpl<Subject, SubjectInfo> implements ISubjectService{
	//private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private ISubjectDao subjectDao;
	private IDataServer dataServer;
	private ICatalogDao catalogDao;
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}
	/**
	 * 设置 远程数据接口
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
	}
	
	/**
	 * 设置 课程分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
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
		if(ids == null ||ids.length==0) return;
		for(String id:ids){
			Subject data = this.subjectDao.load(Subject.class, id);
			if(data != null){
				this.subjectDao.delete(data);
				//TODO 是否连删
			}
		}
	}
	
	@Override
	public Map<String, Object> findChanged() {
		List<Catalog> data = this.dataServer.loadCatalogs();
		List<Subject> subjects = getSubjects(data);
		Map<String,Object> map = new HashMap<String,Object>();
		List<Subject> add = new ArrayList<Subject>();
		List<Subject> update = new ArrayList<Subject>();
		for(Subject s:subjects){
			Subject local_s = this.subjectDao.load(Subject.class, s.getCode());
			if(local_s == null){
				add.add(s);
			}else if(local_s.equals(s)){
				continue;
			}else{
				update.add(s);
			}
		}
		map.put("ADD", add);
		map.put("UPDATE", update);
		return map;
	}
	private List<Subject> getSubjects(List<Catalog> catalogs){
		List<Subject> data = new ArrayList<Subject>();
		for(Catalog c:catalogs){
			if(c.getChildren()!=null && c.getChildren().size()>0){
				Set<Catalog> children = c.getChildren();
				for(Catalog child : children){
					if(child.getSubjects()!=null && child.getSubjects().size()>0){
						data.addAll(child.getSubjects());
					}
				}
			}
		}
		return data;
	}
	private List<Subject> findChangedSubject(){
		List<Catalog> data = this.dataServer.loadCatalogs();
		List<Subject> subjects = getSubjects(data);
		List<Subject> add = new ArrayList<Subject>();
		StringBuffer existIds = new StringBuffer();
		System.out.println(subjects.size());
		for(Subject s:subjects){
			if(!StringUtils.isEmpty(s.getCode())) existIds.append(s.getCode()).append(",");
			Subject local_s = this.subjectDao.load(Subject.class, s.getCode());
			if(local_s == null){
				s.setStatus("新增");
				add.add(s);
			}else if(s.equals(local_s)){
				continue;
			}else{
				s.setStatus("新的");
				//local_s.setStatus("旧的");
				add.add(s);
				//add.add(local_s);
			}
		}
		if(existIds.length()>0)
		{
			existIds.append("0");
			System.out.println(existIds);
			List<Subject> deleteList = this.subjectDao.findDeleteSubjects(existIds.toString());
			if(deleteList!=null && deleteList.size()>0)
			{
				for(Subject s:deleteList){
					s.setStatus("被删");
				}
				add.addAll(deleteList);
			}
		}
		return add;
	}
	@Override
	public DataGrid<SubjectInfo> dataGridUpdate() {
		List<Subject> list = this.findChangedSubject();
		DataGrid<SubjectInfo> grid = new DataGrid<SubjectInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal((long) list.size());
		return grid;
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
			}
			this.subjectDao.saveOrUpdate(changeModel(info));
		}
		if(buf.length()>0)
			this.delete(buf.toString().split(","));
	}
	private Subject changeModel(SubjectInfo info){
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
}
