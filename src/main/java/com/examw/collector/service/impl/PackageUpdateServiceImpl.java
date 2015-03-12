package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogDao;
import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.IErrorRecordDao;
import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.dao.IPackDao;
import com.examw.collector.dao.IPackageEntityDao;
import com.examw.collector.dao.ISubjectDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.dao.IUpdateRecordDao;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.ErrorRecord;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.UpdateRecord;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.PackageEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IPackageUpdateService;
import com.examw.collector.support.JSONUtil;
import com.examw.model.DataGrid;

/**
 * 套餐数据更新服务接口实现类
 * 
 * @author fengwei.
 * @since 2014年7月9日 下午5:12:19.
 */
public class PackageUpdateServiceImpl implements IPackageUpdateService {
	private IPackDao packDao;
	private ISubjectDao subjectDao;
	private ICatalogDao catalogDao;
	private IPackageEntityDao packageEntityDao;
	private ISubjectEntityDao subjectEntityDao;
	private ICatalogEntityDao catalogEntityDao;
	private IOperateLogDao operateLogDao;
	private IDataServer dataServer;
	private IErrorRecordDao errorRecordDao;
	private IUpdateRecordDao updateRecordDao;
	/**
	 * 设置 远程数据接口
	 * @param dataServer
	 * 
	 */
	public void setDataServer(IDataServer dataServer) {
		this.dataServer = dataServer;
	}

	/**
	 * 设置操作日志数据接口
	 * 
	 * @param operateLogDao
	 * 
	 */
	public void setOperateLogDao(IOperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}

	/**
	 * 设置 远程套餐数据接口
	 * 
	 * @param packDao
	 * 
	 */
	public void setPackDao(IPackDao packDao) {
		this.packDao = packDao;
	}

	/**
	 * 设置 远程科目数据接口
	 * 
	 * @param subjectDao
	 * 
	 */
	public void setSubjectDao(ISubjectDao subjectDao) {
		this.subjectDao = subjectDao;
	}

	/**
	 * 设置 远程分类数据接口
	 * 
	 * @param catalogDao
	 * 
	 */
	public void setCatalogDao(ICatalogDao catalogDao) {
		this.catalogDao = catalogDao;
	}

	/**
	 * 设置 本地套餐数据接口
	 * 
	 * @param packageEntityDao
	 * 
	 */
	public void setPackageEntityDao(IPackageEntityDao packageEntityDao) {
		this.packageEntityDao = packageEntityDao;
	}

	/**
	 * 设置 本地科目数据接口
	 * 
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}

	/**
	 * 设置 本地分类数据接口
	 * 
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
	}
	
	/**
	 * 设置 错误记录数据接口
	 * @param errorRecordDao
	 * 
	 */
	public void setErrorRecordDao(IErrorRecordDao errorRecordDao) {
		this.errorRecordDao = errorRecordDao;
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
	public List<PackInfo> update(List<PackInfo> packs,String account) {
		if (packs == null || packs.size() == 0)
			return new ArrayList<PackInfo>();
		List<PackInfo> list = new ArrayList<PackInfo>();
		for (PackInfo info : packs) {
			if (StringUtils.isEmpty(info.getStatus())
					|| info.getStatus().equals("旧的")) {
				continue;
			}
			if (info.getStatus().equals("被删")) {
				Pack data1= this.packDao.load(Pack.class, info.getCode());
				PackageEntity data2 = this.packageEntityDao.load(
						PackageEntity.class, info.getCode());
				if (data2!=null && data1 != null) {
					this.packDao.delete(data1);
				}
				if (data2 != null) {
					this.packageEntityDao.delete(data2);
					list.add(info);
				}
				continue;
			}
			Pack pack = this.changeRemoteModel(info);
			PackageEntity p = changeLocalModel(info);
			if(pack!=null && p!=null)
				this.packDao.saveOrUpdate(pack);
			if(p != null)
			{
				this.packageEntityDao.saveOrUpdate(p);
				list.add(info);
			}
		}
		// 添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_PACKAGE);
		log.setName("更新套餐数据");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent(JSONUtil.ObjectToJson(list));
		this.operateLogDao.save(log);
		return list;
	}
	/**
	 * 本地套餐数据模型转换
	 * @param info
	 * @return
	 */
	private Pack changeRemoteModel(PackInfo info) {
		if (info == null)
			return null;
		Pack data = new Pack();
		BeanUtils.copyProperties(info, data);
		if (StringUtils.isEmpty(info.getCatalogId())) {
			return null;
		} else {
			if (StringUtils.isEmpty(info.getSubjectId())) {
				Catalog catalog = this.catalogDao.load(Catalog.class,
						info.getCatalogId());
				if (catalog == null)
					return null;
				data.setCatalog(catalog);
			} else {
				Subject subject = this.subjectDao.load(Subject.class,
						info.getSubjectId());
				if (subject == null)
					return null;
				data.setSubject(subject);
				data.setCatalog(subject.getCatalog());
			}
		}
		return data;
	}

//	private void updateLocal(List<PackInfo> packs) {
//		if (packs == null || packs.size() == 0)
//			return;
//		List<PackInfo> list = new ArrayList<PackInfo>();
//		for (PackInfo info : packs) {
//			if (StringUtils.isEmpty(info.getStatus())
//					|| info.getStatus().equals("旧的")) {
//				continue;
//			}
//			if (info.getStatus().equals("被删")) {
//				PackageEntity data = this.packageEntityDao.load(
//						PackageEntity.class, info.getCode());
//				if (data != null) {
//					this.packageEntityDao.delete(data);
//					list.add(info);
//				}
//				continue;
//			}
//			PackageEntity p = changeLocalModel(info);
//			if(p != null)
//			{
//				//TODO	套餐价格不同的情况,要酌情更新
//				this.packageEntityDao.saveOrUpdate(p);
//				list.add(info);
//			}
//		}
//		// 添加操作日志
//		OperateLog log = new OperateLog();
//		log.setId(UUID.randomUUID().toString());
//		log.setType(OperateLog.TYPE_UPDATE_PACKAGE);
//		log.setName("更新套餐数据(实际数据)");
//		log.setAddTime(new Date());
//		log.setContent(JSONUtil.ObjectToJson(list));
//		this.operateLogDao.save(log);
//	}
	
	/**
	 * 实际套餐数据模型转换
	 * @param info
	 * @return
	 */
	private PackageEntity changeLocalModel(PackInfo info) {
		if (info == null)
			return null;
		PackageEntity data = this.packageEntityDao.load(PackageEntity.class, info.getCode());
		if(data==null)
			data = new PackageEntity();
		//	套餐价格不同的情况,要酌情更新,售价高于环球的售价,改
		else if(data.getDiscount() <= info.getDiscount())
		{
			info.setDiscount(data.getDiscount());	//售价比环球售价要低[这里没有进行价格修改]
		}
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if (StringUtils.isEmpty(info.getCatalogId())) {
			return null;
		} else {
			if (StringUtils.isEmpty(info.getSubjectId())) {
				CatalogEntity catalog = this.catalogEntityDao.find(info
						.getCatalogId());
				if (catalog == null)
					return null;
				data.setCatalogEntity(catalog);
			} else {
				SubjectEntity subject = this.subjectEntityDao.load(
						SubjectEntity.class, info.getSubjectId());
				if (subject == null)
					return null;
				data.setSubjectEntity(subject);
				data.setCatalogEntity(subject.getCatalogEntity());
			}
		}
		return data;
	}
	
	//=========================================================================
	@Override
	public DataGrid<PackInfo> dataGridUpdate(String account) {
		List<PackInfo> list = this.update(account);
		DataGrid<PackInfo> grid = new DataGrid<PackInfo>();
		grid.setRows(list);
		grid.setTotal((long) list.size());
		return grid;
	}
	public List<PackInfo> update(String account) {
		//找出需要查找并且有变化的科目集合
		List<Pack> packs = this.findChangedPacks();
		List<Pack> listForShow = new ArrayList<Pack>();
		if(packs == null || packs.size()==0){
			OperateLog log = new OperateLog();
			log.setId(UUID.randomUUID().toString());
			log.setType(OperateLog.TYPE_UPDATE_PACKAGE);
			log.setName("更新套餐数据");
			log.setAddTime(new Date());
			log.setAccount(account);
			log.setContent("[ ]");
			this.operateLogDao.save(log);
			return null;
		}
		for (Pack info : packs) {
			if (StringUtils.isEmpty(info.getStatus())
					|| info.getStatus().equals("旧的")) {
				continue;
			}
			if (info.getStatus().equals("被删")) {
				Pack data1= this.packDao.load(Pack.class, info.getCode());
				PackageEntity data2 = this.packageEntityDao.load(
						PackageEntity.class, info.getCode());
				if (data1 != null) {	//08.23修改了下
					this.packDao.delete(data1);
				}
				if (data2 != null) {
					this.packageEntityDao.delete(data2);
					//listForShow.add(info);
				}else{
					info.setUpdateInfo("<span style='color:purple'>删除失败,数据不存在或已被删除</span>"+info.getUpdateInfo());
				}
				listForShow.add(info);
				continue;
			}
			//如果页面中没有这个ID,不要进行操作
			if(info.getStatus().equals("页面无此ID")){
				info.setUpdateInfo("<span style='color:purple'>插入或更新失败</span>"+info.getUpdateInfo());
				ErrorRecord error = this.errorRecordDao.find(info.getCode(),ErrorRecord.TYPE_ERROR_PACK);
				if(error == null){
					listForShow.add(info);
					error = new ErrorRecord(UUID.randomUUID().toString(),info.getCode(),
							"页面无此ID",info.getUpdateInfo(),ErrorRecord.TYPE_ERROR_PACK,"插入失败",new Date(),info.getCatalog().getMyId());
					this.errorRecordDao.save(error);
				}
				continue;
			}
			if(info.getStatus().equals("页面有数据库没有")){
				info.setUpdateInfo("<span style='color:purple'>插入或更新失败</span>"+info.getUpdateInfo());
				listForShow.add(info);
				continue;
			}
			Pack pack = this.judgeDataSafe(info);
			PackageEntity p = changeModel(info);
			if(pack!=null && p!=null)
				this.packDao.saveOrUpdate(pack);
			if(p != null)
			{
				this.packageEntityDao.saveOrUpdate(p);
				info.setStatus("更新成功");
				listForShow.add(info);
			}else{
				info.setUpdateInfo("<span style='color:purple'>插入或更新失败</span>"+info.getUpdateInfo());
				ErrorRecord error = this.errorRecordDao.find(info.getCode(),ErrorRecord.TYPE_ERROR_PACK);
				if(error == null){
					listForShow.add(info);
					error = new ErrorRecord(UUID.randomUUID().toString(),info.getCode(),
							"找不到科目",info.getUpdateInfo(),ErrorRecord.TYPE_ERROR_PACK,"插入失败",new Date(),info.getCatalog().getMyId());
					this.errorRecordDao.save(error);
				}
			}
		}
		List<PackInfo> result = this.changeModel(listForShow);
		addToUpdateRecord(listForShow);
		// 添加操作日志
		OperateLog log = new OperateLog();
		log.setId(UUID.randomUUID().toString());
		log.setType(OperateLog.TYPE_UPDATE_PACKAGE);
		log.setName("更新套餐数据");
		log.setAddTime(new Date());
		log.setAccount(account);
		log.setContent(JSONUtil.ObjectToJson(result));
		this.operateLogDao.save(log);
		return result;
	}
	/**
	 * 找出有变化的集合
	 * @return
	 */
	private List<Pack> findChangedPacks(){
		//需要进行更新比对的分类
		List<CatalogEntity> needList = this.catalogEntityDao.findAllWithCode();
		List<Pack> packList = new ArrayList<Pack>();
		PackInfo info = new PackInfo();
		for(CatalogEntity entity : needList){
			String[] arr = entity.getCode().split(",");
			String[] pages = null;
			if(!StringUtils.isEmpty(entity.getPageUrl()))
				 pages = entity.getPageUrl().split(",");
			for(int i=0;i<arr.length;i++)
			{
				if(StringUtils.isEmpty(arr[i])) continue;
				String page = pages==null?"":pages.length==1?pages[0]:pages[i];
				info.setCatalogId(arr[i]);
				packList.addAll(this.findChangedPack(entity.getId(),info,page));
			}
		}
		return packList;
	}
	private List<Pack> findChangedPack(String classId,PackInfo info,String page){
		Catalog catalog = this.catalogDao.load(Catalog.class, info.getCatalogId());
		catalog.setMyId(classId); //设置自己的分类
		List<Pack> data = this.dataServer.loadPacks(info.getCatalogId(), null);
		List<Pack> add = new ArrayList<Pack>();
		if(data == null) return add;
		StringBuffer existIds = new StringBuffer();
		//*******取消页面的数据的采集比对	2014.09.23***************************************
		//String ids = this.dataServer.loadPagePackIds(page);
		String ids = "";
		//*******取消页面的数据的采集比对 2014.09.23***************************************
		findMissId(catalog,data,ids,add);	//查询页面上有,但是拿到的数据没有的情况
		if(!StringUtils.isEmpty(ids)){
			existIds.append("'0'").append(ids.replaceAll("("+DataServerImpl.ID_PREFIX+"\\d+)", "'$1'"));	//如果不为空,页面上的ID就是应该存在的ID
		}
		for(Pack p:data){
			if(p==null) continue;
			p.setCatalog(catalog);
			if(!StringUtils.isEmpty(p.getCode())&& StringUtils.isEmpty(ids)) existIds.append("'"+p.getCode()+"'").append(",");
			Pack local_p = this.packDao.load(Pack.class, p.getCode());
			if(local_p == null){
				p.setStatus("新增");
				p.setUpdateInfo("<span style='color:blue'>[新增]</span>"+p.toString());
				addSubject(p,ids);
				add.add(p);
			}else if(p.equals(local_p)){
				continue;
			}else{
				//TODO 套餐价格不同的情况 要进行提醒
				p.setStatus("新的");
				//价格变化,特别提醒
				if(!p.getDiscount().equals(local_p.getDiscount()))
				{
					//查询实际数据比较价格
					PackageEntity real_p = this.packageEntityDao.load(PackageEntity.class, p.getCode());
					if(real_p != null){
						p.setUpdateInfo("<span style='color:red'>！价格变化,我们的售价为:"+real_p.getDiscount()+";新售价:"+p.getDiscount()+"</span>"+p.getUpdateInfo());
					}
				}
				//local_p.setStatus("旧的");
				p.setUpdateInfo("<span style='color:red'>[更新]</span>"+p.getUpdateInfo());
				BeanUtils.copyProperties(p, local_p);	//已经存在的,必须用原有的数据进行更新,不然会出错
				local_p.setCatalog(catalog);
				add.add(local_p);
				//add.add(local_p);
			}
		}
		if(existIds.length()>0)
		{
			existIds.append("'0'");
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
		return add;
	}
	
	private Pack judgeDataSafe(Pack info)
	{
		if (info == null)
			return null;
		if (StringUtils.isEmpty(info.getCode())) {
			return null;
		} else {
			if (info.getSubject()==null) {
				Catalog catalog = this.catalogDao.load(Catalog.class,
						info.getCatalog().getCode());
				if (catalog == null)
					return null;
				info.setCatalog(catalog);
			} else {
				Subject subject = this.subjectDao.load(Subject.class,
						info.getSubject().getCode());
				if (subject == null)
					return null;
				info.setSubject(subject);
//				info.setCatalog(subject.getCatalog());
			}
		}
		return info;
	}
	private PackageEntity changeModel(Pack info)
	{
		if (info == null)
			return null;
		PackageEntity data = this.packageEntityDao.load(PackageEntity.class, info.getCode());
		if(data==null)
			data = new PackageEntity();
		//	套餐价格不同的情况,要酌情更新,售价高于环球的售价,改
//		else if(data.getDiscount() <= info.getDiscount())
//		{
//			info.setDiscount(data.getDiscount());	//售价比环球售价要低[这里没有进行价格修改]
//		}
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if (StringUtils.isEmpty(info.getCode())) {
			return null;
		} else {
			if (info.getSubject()==null) {
				CatalogEntity catalog = this.catalogEntityDao.find(info
						.getCatalog().getCode());
				if (catalog == null){
					info.setUpdateInfo(info.getUpdateInfo()+" !!没有找到相应的分类!!");
					return null;
				}
				data.setCatalogEntity(catalog);
				if("新的".equals(info.getStatus())){
					info.setUpdateInfo(info.getUpdateInfo()+"   所属类别:"+info.getCatalog().getName()+"("+info.getCatalog().getCode()+");");
				}
			} else {
				SubjectEntity subject = this.subjectEntityDao.load(
						SubjectEntity.class, info.getSubject().getCode());
				if (subject == null){
					info.setUpdateInfo(info.getUpdateInfo()+" !!没有找到相应的科目!!");
					return null;
				}
				data.setSubjectEntity(subject);
				data.setCatalogEntity(subject.getCatalogEntity());
				if("新的".equals(info.getStatus())){
					info.setUpdateInfo(info.getUpdateInfo()	+("所属科目:"+subject.getName()+"("+subject.getId()+");"));
				}
			}
		}
		return data;
	}
	private List<PackInfo> changeModel(List<Pack> packs)
	{
		List<PackInfo> list = new ArrayList<>();
		if(packs != null && packs.size() > 0){
			for(Pack data : packs){
				PackInfo info = this.change2InfoModel(data);
				if(info != null){
					list.add(info);
				}
			}
		}
		return list;
	}
	private PackInfo change2InfoModel(Pack data)
	{
		if (data == null)
			return null;
		PackInfo info = new PackInfo();
		BeanUtils.copyProperties(data, info);
		// 设置科目
		if (data.getSubject() != null) {
			info.setSubjectId(data.getSubject().getCode());
			info.setSubjectName(data.getSubject().getName());
		}
		Catalog c = data.getCatalog();
		if( c!= null){
			info.setCatalogId(c.getCode());
			info.setCatalogName(c.getName());
		}
		return info;
	}
	
	/**
	 * 页面上有的,但是实际没有的科目进行添加
	 * @param info
	 * @param ids
	 */
	private void addSubject(Pack info,String ids)
	{
		if(info==null||StringUtils.isEmpty(ids)) return;
		if(ids.contains(","+info.getCode()+",")){	//如果页面上包含这个班级
			if(info.getSubject() == null) return;
			Subject s = this.subjectDao.load(Subject.class, info.getSubject().getCode());
			if(s == null)	//实际数据库中找不到这个
			{
				//把这个科目加上
				s = info.getSubject();
				s.setAdd("1"); 	//表示是自己加上的
				s.setCatalog(info.getCatalog());
				subjectDao.save(s);
				//实际数据库也加上
				SubjectEntity entity = new SubjectEntity();
				entity.setId(s.getCode());
				entity.setName(s.getName());
				entity.setCatalogEntity(this.catalogEntityDao.find(info.getCatalog().getCode()));
				subjectEntityDao.save(entity);
				//添加记录
				info.setUpdateInfo(info.getUpdateInfo()+" !!![同时新增科目]!!!");
			}
		}else{
			info.setStatus("页面无此ID");
			info.setUpdateInfo("<span style='color:#777777'>[页面不存在此ID]</span><span style='color:blue'>[新增]</span>"+info.toString());
		}
	}
	
	private void addToUpdateRecord(List<Pack> list){
		if(list.size() == 0) return;
		for(Pack info:list){
			UpdateRecord data = new UpdateRecord();
			data = new UpdateRecord(UUID.randomUUID().toString(),info.getCode(),
					info.getName(),info.getUpdateInfo(),UpdateRecord.TYPE_UPDATE_PACK,"更新成功".equals(info.getStatus())?"更新成功":"更新失败",new Date(),info.getCatalog().getMyId());
			this.updateRecordDao.save(data);
		}
	}
	/**
	 * 查询页面上有但是拿到的数据没有对应的ID号
	 * @param catalog	分类
	 * @param data	拿到的数据
	 * @param ids	采集的ID号
	 * @param add	需要进行修改的数据集合
	 */
	private void findMissId(Catalog catalog,List<Pack> data,String ids,List<Pack> add){
		if(StringUtils.isEmpty(ids)) return;
		try{
			String[] arr = ids.split(",");
			for(int i =1;i<arr.length;i++){
				boolean exist = false;
				for(Pack s:data){
					if(arr[i].equals(s.getCode())){
						exist = true;
						break;
					}
				}
				if(!exist){
					Pack sub = new Pack();
					sub.setCode(arr[i]);
					sub.setStatus("页面有数据库没有");
					sub.setCatalog(catalog);
					sub.setUpdateInfo("[页面存在但没有数据]!!!需要手动操作!!! 所属考试:"+catalog.getName()+"("+catalog.getCode()+"),套餐的ID号:"+arr[i]);
					add.add(sub);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
