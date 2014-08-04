package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.dao.IPackDao;
import com.examw.collector.dao.IPackageEntityDao;
import com.examw.collector.dao.ISubjectEntityDao;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.local.CatalogEntity;
import com.examw.collector.domain.local.PackageEntity;
import com.examw.collector.domain.local.SubjectEntity;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IPackageEntityService;

/**
 * 套餐服务接口实现类
 * @author fengwei.
 * @since 2014年7月1日 上午9:59:06.
 */
public class PackageEntityServiceImpl extends BaseDataServiceImpl<PackageEntity, PackInfo>
		implements IPackageEntityService {
	private static Logger logger = Logger.getLogger(PackageEntityServiceImpl.class);
	private IPackageEntityDao packageEntityDao;
	//private IDataServer dataServer;
	private IPackDao packDao;
	private ISubjectEntityDao subjectEntityDao;
	private ICatalogEntityDao catalogEntityDao;
	/**
	 * 设置 套餐数据接口
	 * 
	 * @param packDao
	 * 
	 */
	public void setPackageEntityDao(IPackageEntityDao packageEntityDao) {
		this.packageEntityDao = packageEntityDao;
	}
	
	/**
	 * 设置 套餐数据接口
	 * @param catalogEntityDao
	 * 
	 */
	public void setCatalogEntityDao(ICatalogEntityDao catalogEntityDao) {
		this.catalogEntityDao = catalogEntityDao;
	}

	/**
	 * 设置 
	 * @param subjectEntityDao
	 * 
	 */
	public void setSubjectEntityDao(ISubjectEntityDao subjectEntityDao) {
		this.subjectEntityDao = subjectEntityDao;
	}

	/**
	 * 设置 远程数据接口
	 * 
	 * @param dataServer
	 * 
	 */
//	public void setDataServer(IDataServer dataServer) {
//		this.dataServer = dataServer;
//	}
	
	/**
	 * 设置 远程套餐数据接口
	 * @param packDao
	 * 
	 */
	public void setPackDao(IPackDao packDao) {
		this.packDao = packDao;
	}

	@Override
	public void init(PackInfo info) {
		if (info == null)
			return;
		logger.info("开始初始化套餐...");
//		List<Pack> data = this.dataServer.loadPacks(info.getCatalogId(),
//				info.getSubjectId());
		List<Pack> data = this.packDao.findPacks(info);
		if (data != null && data.size() > 0) {
			//setSubClassRelationShip(data);
			List<PackageEntity> list = new ArrayList<PackageEntity>();
			for(Pack p:data){
				list.add(this.changeData(p));
			}
			this.packageEntityDao.batchSave(list);
		}
		logger.info("初始化完成！");
	}
	
	private PackageEntity changeData(Pack pack){
		if(pack == null) return null;
		PackageEntity data = new PackageEntity();
		BeanUtils.copyProperties(pack, data);
		if(pack.getStudentPrice()==null){
			data.setStudentPrice((int) (pack.getDiscount()*0.9));
		}
		data.setId(pack.getCode());
		if(pack.getCatalog()!=null){
			CatalogEntity c = new CatalogEntity();
			c.setCode(pack.getCatalog().getCode());
			data.setCatalogEntity(c);
		}
		if(pack.getSubject()!=null){
			SubjectEntity s = new SubjectEntity();
			s.setId(pack.getSubject().getCode());
			data.setSubjectEntity(s);
		}
		return data;
	}
	// 不这么弄,里面有些的班级没有
	/*private void setSubClassRelationShip(List<Pack> data) {
		Set<SubClass> classes = new HashSet<SubClass>();
		for (Pack p : data) {
			if (!StringUtils.isEmpty(p.getClassCodes())) {
				String[] codes = p.getClassCodes().split(",");
				if (codes != null && codes.length > 0) {
					for (String code : codes) {
						// SubClass subClass =
						// this.subClassDao.load(SubClass.class, code);
						// if(subClass!=null){
						SubClass subClass = new SubClass();
						subClass.setCode(code);
						classes.add(subClass);
						// }
					}
					p.setSubClasses(classes);
				}
			}
		}
	}*/

	@Override
	protected List<PackageEntity> find(PackInfo info) {
		return this.packageEntityDao.findPacks(info);
	}

	@Override
	protected PackInfo changeModel(PackageEntity data) {
		if (data == null)
			return null;
		PackInfo info = new PackInfo();
		BeanUtils.copyProperties(data, info);
		info.setCode(data.getId());
		// 设置科目
		if (data.getSubjectEntity() != null) {
			info.setSubjectId(data.getSubjectEntity().getId());
			info.setSubjectName(data.getSubjectEntity().getName());
		}
		if (data.getCatalogEntity() != null) {
			info.setCatalogId(data.getCatalogEntity().getCode());
			info.setCatalogName(data.getCatalogEntity().getCname());
		}
		return info;
	}

	@Override
	protected Long total(PackInfo info) {
		return this.packageEntityDao.total(info);
	}

	@Override
	public PackInfo update(PackInfo info) {

		return null;
	}

	@Override
	public void delete(String[] ids) {
		if(ids == null ||ids.length==0) return;
		for(String id:ids){
			PackageEntity data = this.packageEntityDao.load(PackageEntity.class, id);
			if(data != null){
				this.packageEntityDao.delete(data);
			}
		}
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
			this.packageEntityDao.saveOrUpdate(changeModel(info));
		}
		if(buf.length()>0)
			this.delete(buf.toString().split(","));
	}
	private PackageEntity changeModel(PackInfo info){
		if(info == null) return null;
		PackageEntity data = new PackageEntity();
		BeanUtils.copyProperties(info, data);
		data.setId(info.getCode());
		if(StringUtils.isEmpty(info.getCatalogId())){
			return null;
		}else{
			if(StringUtils.isEmpty(info.getSubjectId()))
			{
				CatalogEntity catalog = this.catalogEntityDao.find(info.getCatalogId());
				if(catalog == null) return null;
				data.setCatalogEntity(catalog);
			}else{
				SubjectEntity subject = this.subjectEntityDao.load(SubjectEntity.class,info.getSubjectId());
				if(subject==null) return null;
				data.setSubjectEntity(subject);
				data.setCatalogEntity(subject.getCatalogEntity());
			}
		}
		return data;
	}
}
