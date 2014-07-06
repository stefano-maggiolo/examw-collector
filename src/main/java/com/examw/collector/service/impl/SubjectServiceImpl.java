package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Subject;
import com.examw.collector.model.CatalogInfo;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.ISubjectService;
import com.examw.model.DataGrid;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午3:55:20.
 */
public class SubjectServiceImpl extends BaseDataServiceImpl<Subject, SubjectInfo> implements ISubjectService{
	//private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private ISubjectDao subjectDao;
	private IDataServer dataServer;
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
		for(Subject s:subjects){
			Subject local_s = this.subjectDao.load(Subject.class, s.getCode());
			if(local_s == null){
				s.setStatus("新增");
				add.add(s);
			}else if(local_s.equals(s)){
				continue;
			}else{
				s.setStatus("新的");
				local_s.setStatus("旧的");
				add.add(s);
				add.add(local_s);
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
}
