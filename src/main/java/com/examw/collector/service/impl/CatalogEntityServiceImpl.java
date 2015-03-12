package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.local.CatalogEntityInfo;
import com.examw.collector.service.ICatalogEntityService;
import com.examw.model.DataGrid;
import com.examw.model.TreeNode;

/**
 * 课程分类服务接口实现类
 * @author fengwei.
 * @since 2014年7月2日 上午10:01:04.
 */
public class CatalogEntityServiceImpl  extends BaseDataServiceImpl<CatalogEntity, CatalogEntityInfo> implements ICatalogEntityService{
	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private ICatalogEntityDao catalogEntityDao;
	private IOperateLogDao operateLogDao;
	private ICatalogDao catalogDao;
	private ISubjectEntityDao subjectEntityDao;
	/**
	 * 设置 考试类别数据接口
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
	}
	/**
	 * 设置操作日志数据接口
	 * @param operateLogDao
	 * 
	 */
	public void setOperateLogDao(IOperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}
	
	/**
	 * 设置 环球分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
	/**
	 * 设置 本地科目数据接口
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}
	@Override
	protected List<CatalogEntity> find(CatalogEntityInfo info) {
		return this.catalogEntityDao.findCatalogs(info);
	}
	@Override
	protected CatalogEntityInfo changeModel(CatalogEntity data) {
		if(data == null) return null;
		CatalogEntityInfo info = new CatalogEntityInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		info.setId(data.getId());
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<CatalogEntityInfo> children = new ArrayList<>();
			for(CatalogEntity m : data.getChildren()){
				CatalogEntityInfo c = this.changeModel(m);
				//非环球的子类不加载
				if(c != null && !StringUtils.isEmpty(c.getSchoolId()) && CatalogEntity.SCHOOLID_EDU24.contains(","+c.getSchoolId()+",")){
					c.setPid(data.getId());
					children.add(c);
				}
			}
			info.setChildren(children);
		}
		return info;
	}
	@Override
	protected Long total(CatalogEntityInfo info) {
		
		return null;
	}
	@Override
	public CatalogEntityInfo update(CatalogEntityInfo info) {
		return null;
	}
	@Override
	public CatalogEntityInfo update(CatalogEntityInfo info,String account) {
		if(info == null)	return null;
		if(!StringUtils.isEmpty(info.getId())){
			CatalogEntity data = this.catalogEntityDao.load(CatalogEntity.class, info.getId());
			if(data == null) return null;
			//BeanUtils.copyProperties(info, data);
			//只设置Code 和 pageUrl
			data.setCode(info.getCode());
			data.setPageUrl(info.getPageUrl());
			//插入本地的科目数据
			updateSubjectEntity(data,info.getCode());
			//添加操作日志
			OperateLog log = new OperateLog();
			log.setId(UUID.randomUUID().toString());
			log.setType(OperateLog.TYPE_COMPARECODE);
			log.setName("设置课程分类中环球的code");
			log.setAddTime(new Date());
			log.setAccount(account);
			log.setContent("设置课程分类("+data.getCname()+")中环球的Code为"+info.getCode());
			this.operateLogDao.save(log);
			info = this.changeModel(data);
			return info;
		}
		logger.info("更新失败");
		return null;
	}
	@Override
	public void delete(String[] ids) {
	}
	
	//更新code的同时 要更新科目信息
	public void updateSubjectEntity(CatalogEntity data,String code){
		if(StringUtils.isEmpty(code)){
			//将本地的科目删除
			return;
		}
		String[] codes = code.split(",");
		for(String catalogId:codes){
			//查询远程科目 插入到本地科目表中
			Catalog catalog = this.catalogDao.load(Catalog.class, catalogId);
			importLocalSubject(data,catalog.getSubjects());
		}
	}
	/**
	 * 插入科目数据
	 * 
	 * @param id
	 */
	private void importLocalSubject(CatalogEntity catalog,Set<Subject> subjects) {
		// 转型并插入
		if (subjects == null || subjects.size() == 0)
			return;
		for (Subject s : subjects) {
			SubjectEntity data = this.changeSubjectModel(catalog,s);
			if (data != null)
				this.subjectEntityDao.saveOrUpdate(data);
		}
	}
	/**
	 * 科目数据转换
	 * 
	 * @param info
	 * @return
	 */
	private SubjectEntity changeSubjectModel(CatalogEntity catalog,Subject info) {
		if (info == null)
			return null;
		SubjectEntity data = new SubjectEntity();
		data.setId(info.getCode());
		data.setName(info.getName());
		data.setCatalogEntity(catalog);
		return data;

	}
	@Override
	public DataGrid<CatalogEntityInfo> datagrid(CatalogEntityInfo info) {
		DataGrid<CatalogEntityInfo> grid = new DataGrid<CatalogEntityInfo>();
		grid.setRows(this.changeModel(this.find(info)));
		grid.setTotal((long) grid.getRows().size());
		return grid;
	}
	
	@Override
	public List<TreeNode> loadAllCatalogs() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<CatalogEntity> catalogs = this.find(new CatalogEntityInfo());
		if(catalogs != null && catalogs.size() > 0){
			for(CatalogEntity catalog : catalogs){
				treeNodes.add(this.turnToTree(catalog));
			}
		}
		return treeNodes;
	}
	/**
	 * 单个类型转换为treeNode
	 * @param data
	 * @return
	 */
	private TreeNode turnToTree(CatalogEntity data){
		if(data == null) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getId());
		node.setText(data.getCname()+"(环球code:"+data.getCode()+")");
		if(!StringUtils.isEmpty(data.getCode()))
		{
			Map<String, Object> attributes = new HashMap<>();
			attributes.put("code", data.getCode());
			node.setAttributes(attributes);
		}
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<>();
			for(CatalogEntity m : data.getChildren()){
				TreeNode c = this.turnToTree(m);
				if(c != null){
					children.add(c);
				}
			}
			node.setChildren(children);
		}
		return node;
	}
}
