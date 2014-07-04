package com.examw.collector.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.examw.collector.dao.IPackageEntityDao;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.local.PackageEntity;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IPackageEntityService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:59:06.
 */
public class PackageEntityServiceImpl extends BaseDataServiceImpl<PackageEntity, PackInfo>
		implements IPackageEntityService {
	private static Logger logger = Logger.getLogger(PackageEntityServiceImpl.class);
	private IPackageEntityDao packageEntityDao;
	private IDataServer dataServer;

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
		data.setId(pack.getCode());
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
		// 设置科目
//		if (data.getSubjectEntity() != null) {
//			info.setSubjectId(data.getSubject().getCode());
//			info.setSubjectName(data.getSubject().getName());
//		}
//		if (data.getCatalog() != null) {
//			info.setCatalogId(data.getCatalog().getCode());
//			info.setCatalogName(data.getCatalog().getName());
//		}
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
	}

}
