package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.IPackDao;
import com.examw.collector.dao.IPackageEntityDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.PackageEntity;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IPackService;
import com.examw.model.DataGrid;

/**
 * 套餐服务接口实现类
 * @author fengwei.
 * @since 2014年7月1日 上午9:59:06.
 */
public class PackServiceImpl extends BaseDataServiceImpl<Pack, PackInfo>
		implements IPackService {
	private static Logger logger = Logger.getLogger(PackServiceImpl.class);
	private IPackDao packDao;
	private IDataServer dataServer;

//	private ISubClassDao subClassDao;
	private ISubjectDao subjectDao;
	private ICatalogDao catalogDao;
	private IOperateLogDao operateLogDao;
	private IPackageEntityDao packageEntityDao;
	/**
	 * 设置操作日志数据接口
	 * @param operateLogDao
	 * 
	 */
	public void setOperateLogDao(IOperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}
	/**
	 * 设置 班级数据接口
	 * 
	 * @param subClassDao
	 * 
	 */
//	public void setSubClassDao(ISubClassDao subClassDao) {
//		this.subClassDao = subClassDao;
//	}

	/**
	 * 设置 套餐数据接口
	 * 
	 * @param packDao
	 * 
	 */
	public void setPackDao(IPackDao packDao) {
		this.packDao = packDao;
	}
	
	/**
	 * 设置 科目数据接口
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	/**
	 * 设置 课程分类数据接口
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}
	
	/**
	 * 设置 套餐数据接口
	 * @param packageEntityDao
	 * 
	 */
	public void setPackageEntityDao(IPackageEntityDao packageEntityDao) {
		this.packageEntityDao = packageEntityDao;
	}

	/**
	 * 设置 远程数据接口
	 * 
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
	}

	@Override
	public void init(PackInfo info) {
		if (info == null)
			return;
		logger.info("开始初始化套餐...");
		List<Pack> data = this.dataServer.loadPacks(info.getCatalogId(),
				info.getSubjectId());
		if (data != null && data.size() > 0) {
			//setSubClassRelationShip(data);
			this.packDao.batchSave(data);
		}
		logger.info("初始化完成！");
	}
	// 不这么弄,里面有些的班级没有
//	private void setSubClassRelationShip(List<Pack> data) {
//		Set<SubClass> classes = new HashSet<SubClass>();
//		for (Pack p : data) {
//			if (!StringUtils.isEmpty(p.getClassCodes())) {
//				String[] codes = p.getClassCodes().split(",");
//				if (codes != null && codes.length > 0) {
//					for (String code : codes) {
//						// SubClass subClass =
//						// this.subClassDao.load(SubClass.class, code);
//						// if(subClass!=null){
//						SubClass subClass = new SubClass();
//						subClass.setCode(code);
//						classes.add(subClass);
//						// }
//					}
//					p.setSubClasses(classes);
//				}
//			}
//		}
//	}

	@Override
	protected List<Pack> find(PackInfo info) {
		return this.packDao.findPacks(info);
	}

	@Override
	protected PackInfo changeModel(Pack data) {
		if (data == null)
			return null;
		PackInfo info = new PackInfo();
		BeanUtils.copyProperties(data, info);
		// 设置科目
		if (data.getSubject() != null) {
			info.setSubjectId(data.getSubject().getCode());
			info.setSubjectName(data.getSubject().getName());
		}
		if (data.getCatalog() != null) {
			info.setCatalogId(data.getCatalog().getCode());
			info.setCatalogName(data.getCatalog().getName());
		}
		return info;
	}

	@Override
	protected Long total(PackInfo info) {
		return this.packDao.total(info);
	}

	@Override
	public PackInfo update(PackInfo info) {

		return null;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null ||ids.length==0) return;
		for(String id:ids){
			Pack data = this.packDao.load(Pack.class, id);
			if(data != null){
				this.packDao.delete(data);
			}
		}
	}
	@Override
	public DataGrid<PackInfo> dataGridUpdate(PackInfo info,String account) {
		if(StringUtils.isEmpty(info.getCatalogId()))
			return null;
		List<Pack> list = this.findChangedPack(info,account);
		DataGrid<PackInfo> grid = new DataGrid<PackInfo>();
		grid.setRows(this.changeModel(list));
		grid.setTotal((long) list.size());
		return grid;
	}
	/**
	 * 找出有变化的套餐
	 * @param info
	 * @return
	 */
	private List<Pack> findChangedPack(PackInfo info,String account){
		Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
		List<Pack> data = this.dataServer.loadPacks(info.getCatalogId(), info.getSubjectId());
		List<Pack> add = new ArrayList<Pack>();
		StringBuffer existIds = new StringBuffer();
		for(Pack p:data){
			p.setCatalog(catalog);
			if(!StringUtils.isEmpty(p.getCode())) existIds.append(p.getCode()).append(",");
			Pack local_p = this.packDao.load(Pack.class, p.getCode());
			if(local_p == null){
				p.setStatus("新增");
				p.setUpdateInfo(p.toString());
				add.add(p);
			}else if(p.equals(local_p)){
				continue;
			}else{
				//TODO 套餐价格不同的情况 要进行提醒
				p.setStatus("新的");
				//价格变化,特别提醒
				if(p.getDiscount() != local_p.getDiscount())
				{
					//查询实际数据比较价格
					PackageEntity real_p = this.packageEntityDao.load(PackageEntity.class, p.getCode());
					if(real_p != null){
						p.setUpdateInfo("<span style='color:red'>！价格变化,我们的售价为:"+real_p.getDiscount()+";新售价:"+p.getDiscount()+"</span>"+p.getUpdateInfo());
					}
				}
				//local_p.setStatus("旧的");
				add.add(p);
				//add.add(local_p);
			}
		}
		if(existIds.length()>0)
		{
			existIds.append("0");
			System.out.println(existIds);
			List<Pack> deleteList = this.packDao.findDeletePacks(existIds.toString(),info);
			if(deleteList!=null && deleteList.size()>0)
			{
				for(Pack s:deleteList){
					s.setStatus("被删");
					s.setUpdateInfo(s.toString());
				}
				add.addAll(deleteList);
			}
		}
		//添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_CHECK_UPDATE);
		log.setName("检测套餐数据更新");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent("检测 "+catalog.getName()+"("+catalog.getCode()+")的套餐数据更新");
		this.operateLogDao.save(log);
		return add;
	}
	
	@Override
	public void update(List<PackInfo> packs) {
		if(packs == null ||packs.size()==0) return;
		StringBuffer buf = new StringBuffer();
		for(PackInfo info:packs){
			if(StringUtils.isEmpty(info.getStatus())||info.getStatus().equals("旧的")){
				continue;
			}
			if(info.getStatus().equals("被删")){
				buf.append(info.getCode()).append(",");
				continue;
			}
			this.packDao.saveOrUpdate(changeModel(info));
		}
		if(buf.length()>0)
			this.delete(buf.toString().split(","));
	}
	/**
	 * 数据转换
	 * @param info
	 * @return
	 */
	private Pack changeModel(PackInfo info){
		if(info == null) return null;
		Pack data = new Pack();
		BeanUtils.copyProperties(info, data);
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			if(StringUtils.isEmpty(info.getSubjectId())){
				Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
				if(catalog == null) return null;
				data.setCatalog(catalog);
			}else{
				Subject subject = this.subjectDao.load(Subject.class, info.getSubjectId());
				if(subject==null) return null;
				data.setSubject(subject);
				data.setCatalog(subject.getCatalog());
			}
		}
		return data;
	}
}
