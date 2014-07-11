package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.domain.local.CatalogEntity;
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
	
	/**
	 * 设置 考试类别数据接口
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
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
				if(c != null && CatalogEntity.SCHOOLID_EDU24.equals(c.getSchoolId())){
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
		if(info == null)	return null;
		if(!StringUtils.isEmpty(info.getId())){
			CatalogEntity data = this.catalogEntityDao.load(CatalogEntity.class, info.getId());
			if(data == null) return null;
			//BeanUtils.copyProperties(info, data);
			//只设置Code
			data.setCode(info.getCode());
			return info;
		}
		logger.info("更新失败");
		return null;
	}
	@Override
	public void delete(String[] ids) {
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
