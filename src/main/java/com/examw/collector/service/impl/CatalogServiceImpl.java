package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Subject;
import com.examw.collector.model.CatalogInfo;
import com.examw.collector.service.ICatalogService;
import com.examw.collector.service.IDataServer;
import com.examw.collector.support.JSONUtil;
import com.examw.model.DataGrid;
import com.examw.model.TreeNode;

/**
 * 课程分类(本地副本)服务接口实现类
 * @author fengwei.
 * @since 2014年6月30日 上午11:36:26.
 */
public class CatalogServiceImpl extends BaseDataServiceImpl<Catalog, CatalogInfo> implements ICatalogService{
	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private ICatalogDao catalogDao;
	private ISubjectDao subjectDao;
	private IDataServer dataServer;
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
		if(catalogs == null || catalogs.size()==0) return data;
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
	
//	@Override
//	public Map<String, Object> findChanged() {
//		List<Catalog> remote = this.dataServer.loadCatalogs();	//远程的数据
//		//List<Catalog> local = this.find(new CatalogInfo());		//本地的所有数据
//		Map<String,Object> map = new HashMap<String,Object>();
//		List<Catalog> add = new ArrayList<Catalog>();
//		List<Catalog> update = new ArrayList<Catalog>();
//		for(Catalog c:remote){
//			Catalog local_c = this.catalogDao.load(Catalog.class, c.getCode());
//			if(local_c == null){
//				add.add(c);
//			}else if(c.equals(local_c)){
//				if(c.getChildren()!=null&&c.getChildren().size()>0){
//					for(Catalog child:c.getChildren()){
//						Catalog local_child = this.catalogDao.load(Catalog.class, child.getCode());
//						if(child.equals(local_child)) continue;
//						else{
//							update.add(c);
//							break;
//						}
//					}
//				}
//			}else{
//				update.add(c);
//			}
//		}
//		map.put("ADD", add);
//		map.put("UPDATE", update);
//		return map;
//	}
	
	public List<Catalog> findChangedCatalog(String account) {
		List<Catalog> remote = this.dataServer.loadCatalogs();	//远程的数据
		//List<Catalog> local = this.find(new CatalogInfo());		//本地的所有数据
		logger.info("查找有变化的考试分类");
		List<Catalog> add = new ArrayList<Catalog>();
		StringBuilder pbuf = new StringBuilder();
		for(Catalog c:remote){
			pbuf.append(c.getCode()).append(",");
			Catalog local_c = this.catalogDao.load(Catalog.class, c.getCode());
			if(local_c == null){
				c.setStatus("新增");
				c.setUpdateInfo("<span style='color:blue'>[新增]</span>"+c.toString());
				add.add(c);
			}else if(c.equals(local_c)){
				if(c.getChildren()!=null&&c.getChildren().size()>0){
					Set<Catalog> children = new TreeSet<Catalog>(new Comparator<Catalog>(){
						@Override
						public int compare(Catalog o1, Catalog o2) {
							int value = o1.getCode().compareTo(o2.getCode());
							if(value==0){
								if("新的".endsWith(o1.getStatus()))
									return -1;
								else
									return 1;
							}
							return value;
						}
					});
					StringBuilder cbuf = new StringBuilder();
					for(Catalog child:c.getChildren()){
						cbuf.append(child.getCode()).append(",");
						Catalog local_child = this.catalogDao.load(Catalog.class, child.getCode());
						if(local_child == null){
							child.setStatus("新增");
							child.setUpdateInfo("<span style='color:blue'>[新增]</span>"+child.toString());
							children.add(child);
						}else if(child.equals(local_child)) continue;
						else{
							child.setStatus("新的");
							child.setUpdateInfo("<span style='color:red'>[新增]</span>"+child.getUpdateInfo());
							children.add(child);
							//local_child.setStatus("旧的");
							//children.add(local_child);
						}
					}
					if(cbuf.length()>0){
						cbuf.append("0");
						List<Catalog> d = this.catalogDao.findDeleteCatalogs(cbuf.toString(), c.getCode());
						if(d!=null&&d.size()>0){
							for(Catalog s:d){
								s.setStatus("被删");
							}
							children.addAll(d);
						}
						cbuf = null;
					}
					if(children.size()>0){
						c.setChildren(children);
						add.add(c);
					}
				}
			}else{
				c.setStatus("新的");
				c.setUpdateInfo("<span style='color:blue'>[新增]</span>"+c.getUpdateInfo());
				//local_c.setStatus("旧的");
				add.add(c);
				//add.add(local_c);
			}
		}
		//查找被删的集合
		if(pbuf.length()>0){
			pbuf.append("0");
			List<Catalog> d = this.catalogDao.findDeleteCatalogs(pbuf.toString(), null);
			if(d!=null&&d.size()>0){
				for(Catalog s:d){
					s.setStatus("被删");
				}
				add.addAll(d);
			}
			pbuf = null;
		}
//		logger.info(add.size());
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_CHECK_UPDATE);
		log.setName("检测课程分类数据更新");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent("检测课程分类数据更新");
		this.operateLogDao.save(log);
		return add;
	}
	/*
	 * 查找有更新的数据
	 * @see com.examw.collector.service.ICatalogService#dataGridUpdate()
	 */
	@Override
	public DataGrid<CatalogInfo> dataGridUpdate(String account) {
		try{
		List<Catalog> list = this.findChangedCatalog(account);
		DataGrid<CatalogInfo> grid = new DataGrid<CatalogInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal((long) list.size());
		return grid;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	/*
	 * 更新数据
	 * @see com.examw.collector.service.ICatalogService#update(java.util.List)
	 */
	@Override
	public void update(List<CatalogInfo> catalogs,String account) {
		if(catalogs == null ||catalogs.size()==0) return;
		List<CatalogInfo> list = new ArrayList<CatalogInfo>();
		for(CatalogInfo info:catalogs){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				Catalog data = this.catalogDao.load(Catalog.class, info.getId());
				if(data != null){
					//删除[子类级联.]
					this.catalogDao.delete(data);
					list.add(info);
				}
				continue;
			}
			Catalog c = this.changeModel(info);
			if(c!=null)
			{
				this.catalogDao.saveOrUpdate(c);
				list.add(info);
			}
		}
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_CATALOG);
		log.setName("更新课程分类数据(本地副本)");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent(JSONUtil.ObjectToJson(list));
		this.operateLogDao.save(log);		
	}
	//转换数据模型
	private Catalog changeModel(CatalogInfo info){
		if(info == null) return null;
		Catalog data = this.catalogDao.load(Catalog.class, info.getId());
		if(data == null){
			//新的分类
			data = new Catalog();
			if(info.getChildren()!=null&&info.getChildren().size()>0){
				Set<Catalog> children = new HashSet<Catalog>();
				for(CatalogInfo ci:info.getChildren()){
					children.add(this.changeModel(ci));
				}
				data.setChildren(children);
			}
		}
		BeanUtils.copyProperties(info, data,new String[]{"children"});
		if(!StringUtils.isEmpty(info.getPid())){
			Catalog c = this.catalogDao.load(Catalog.class, info.getPid());
			if(c != null){
				data.setParent(c);
			}
		}
		return data;
	}
}
