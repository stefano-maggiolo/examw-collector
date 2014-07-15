package com.examw.collector.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.model.OperateLogInfo;
import com.examw.collector.service.IOperateLogService;
/**
 * 登录日志服务实现。
 * @author yangyong.
 * @since 2014-05-17.
 */
public class OperateLogServiceImpl extends BaseDataServiceImpl<OperateLog, OperateLogInfo> implements IOperateLogService {
	private IOperateLogDao operateLogDao;
	private	Map<String,String> typeMap;
	/**
	 * 设置登录日志数据接口。
	 * @param loginLogDao
	 * 数据接口。
	 */
	public void setOperateLogDao(IOperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}
	
	/**
	 * 获取 类型名称映射
	 * @return typeMap
	 * 
	 */
	public Map<String, String> getTypeMap() {
		return typeMap;
	}

	/**
	 * 设置 类型名称映射
	 * @param typeMap
	 * 
	 */
	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}

	/*
	 * 添加登录日志。
	 */
	@Override
	public void addLog(String account, String ip, String browser) {
		if(!StringUtils.isEmpty(account)){
			OperateLog data = new OperateLog();
			data.setId(UUID.randomUUID().toString());
			data.setAccount(account);
			data.setAddTime(new Date());
			this.operateLogDao.save(data);
		}
	}
	/*
	 * 查询数据。
	 */
	@Override
	protected List<OperateLog> find(OperateLogInfo info) {
		return this.operateLogDao.findOperateLogs(info);
	}
	/*
	 * 类型转换。
	 */
	@Override
	protected OperateLogInfo changeModel(OperateLog data) {
		if(data == null) return null;
		OperateLogInfo info = new OperateLogInfo();
		BeanUtils.copyProperties(data, info);
		info.setTypeName(this.getTypeName(data.getType()));
		return info;
	}
	/*
	 * 查询数据统计。
	 */
	@Override
	protected Long total(OperateLogInfo info) {
		return this.operateLogDao.total(info);
	}
	/*
	 * 更新数据。
	 */
	@Override
	public OperateLogInfo update(OperateLogInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		OperateLog data = StringUtils.isEmpty(info.getId()) ? null : this.operateLogDao.load(OperateLog.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			data = new OperateLog();
		}
		BeanUtils.copyProperties(info, data);
		if(isAdded)this.operateLogDao.save(data);
		return info;
	}
	/*
	 * 删除数据。
	 */
	@Override
	public void delete(String[] ids) {
		if(ids == null || ids.length == 0) return;
		for(int i = 0; i < ids.length; i++){
			if(StringUtils.isEmpty(ids[i])) continue;
			OperateLog data = this.operateLogDao.load(OperateLog.class, ids[i]);
			if(data != null) this.operateLogDao.delete(data);
		}
	}
	
	@Override
	public String getTypeName(Integer type) {
		if(typeMap == null ||type == null)
		return null;
		return this.typeMap.get(type.toString());
	}
	
}