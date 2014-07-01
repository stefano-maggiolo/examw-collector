package com.examw.collector.service.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.IPackDao;
import com.examw.collector.dao.ISubClassDao;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.SubClass;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IPackService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:59:06.
 */
public class PackServiceImpl extends BaseDataServiceImpl<Pack, PackInfo>
		implements IPackService {
	private static Logger logger = Logger.getLogger(PackServiceImpl.class);
	private IPackDao packDao;
	private IDataServer dataServer;

	private ISubClassDao subClassDao;

	/**
	 * 设置 班级数据接口
	 * 
	 * @param subClassDao
	 * 
	 */
	public void setSubClassDao(ISubClassDao subClassDao) {
		this.subClassDao = subClassDao;
	}

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
			setSubClassRelationShip(data);
			this.packDao.batchSave(data);
		}
		logger.info("初始化完成！");
	}

	private void setSubClassRelationShip(List<Pack> data) {
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
	}

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
	}

}
