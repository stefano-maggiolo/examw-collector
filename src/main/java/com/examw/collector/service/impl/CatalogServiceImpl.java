package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Subject;
import com.examw.collector.model.CatalogInfo;
import com.examw.collector.service.ICatalogService;
import com.examw.collector.service.IDataServer;
import com.examw.model.DataGrid;
import com.examw.model.TreeNode;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午11:36:26.
 */
public class CatalogServiceImpl extends BaseDataServiceImpl<Catalog, CatalogInfo> implements ICatalogService{
	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private ICatalogDao catalogDao;
	private ISubjectDao subjectDao;
	private IDataServer dataServer;
	
	/**
	 * 设置 分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
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
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	@Override
	protected List<Catalog> find(CatalogInfo info) {
		return this.catalogDao.findCatalogs(info);
	}

	@Override
	protected CatalogInfo changeModel(Catalog data) {
		if(data == null) return null;
		CatalogInfo info = new CatalogInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		info.setId(data.getCode());
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<CatalogInfo> children = new ArrayList<>();
			for(Catalog m : data.getChildren()){
				CatalogInfo c = this.changeModel(m);
				if(c != null){
					c.setPid(data.getCode());
					children.add(c);
				}
			}
			info.setChildren(children);
		}
		//科目不加载
		/*if(data.getSubjects()!=null && data.getSubjects().size() > 0){
			List<CatalogInfo> children = new ArrayList<>();
			for(Subject m : data.getSubjects()){
				CatalogInfo c = new CatalogInfo();
				c.setId(m.getCode());
				c.setName(m.getName());
				c.setPid(data.getCode());
				children.add(c);
			}
			info.setChildren(children);
		}*/
		return info;
	}

	@Override
	protected Long total(CatalogInfo info) {
		
		return null;
	}

	@Override
	public CatalogInfo update(CatalogInfo info) {
		
		return null;
	}

	@Override
	public void delete(String[] ids) {
	}
	
	/**
	 * 初始化课程分类数据。
	 */
	@Override
	public void init(){
		logger.info("开始初始化课程分类...");
		List<Catalog> data = this.dataServer.loadCatalogs();
		if(data!=null &&data.size()>0)
		{
			this.catalogDao.batchSave(data);
		}
		List<Subject> subjects = getSubjects(data);
		if(subjects!=null && subjects.size()>0){
			this.subjectDao.batchSave(subjects);
		}
		logger.info("初始化完成！");
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
	@Override
	public DataGrid<CatalogInfo> datagrid(CatalogInfo info) {
		DataGrid<CatalogInfo> grid = new DataGrid<CatalogInfo>();
		grid.setRows(this.changeModel(this.find(info)));
		grid.setTotal((long) grid.getRows().size());
		return grid;
	}
	
	@Override
	public List<TreeNode> loadAllCatalogs() {
		List<TreeNode> treeNodes = new ArrayList<>();
		List<Catalog> catalogs = this.find(new CatalogInfo());
		if(catalogs != null && catalogs.size() > 0){
			for(Catalog catalog : catalogs){
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
	private TreeNode turnToTree(Catalog data){
		if(data == null) return null;
		TreeNode node = new TreeNode();
		node.setId(data.getCode());
		node.setText(data.getName()+"("+data.getCode()+")");
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<TreeNode> children = new ArrayList<>();
			for(Catalog m : data.getChildren()){
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
