package com.examw.collector.service.impl;
 
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.IUserDao;
import com.examw.collector.domain.User;
import com.examw.collector.model.UserInfo;
import com.examw.collector.service.IUserService;
import com.examw.collector.support.PasswordHelper;

/**
 * 用户服务接口实现。
 * @author yangyong.
 * @since 2014-05-08.
 */
public class UserServiceImpl extends BaseDataServiceImpl<User, UserInfo> implements IUserService {
	private IUserDao userDao;
	private Map<Integer, String> genderNames,statusNames;
	private PasswordHelper passwordHelper;
	
	/**
	 * 设置用户数据接口。
	 * @param userDao
	 * 用户数据接口。
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	/**
	 * 设置密码工具。
	 * @param passwordHelper
	 */
	public void setPasswordHelper(PasswordHelper passwordHelper) {
		this.passwordHelper = passwordHelper;
	}
	/**
	 * 设置性别名称。
	 * @param genderNames
	 * 性别名称。
	 */
	public void setGenderNames(Map<Integer, String> genderNames) {
		this.genderNames = genderNames;
	}
	/**
	 * 设置状态名称。
	 * @param statusNames
	 * 状态名称。
	 */
	public void setStatusNames(Map<Integer, String> statusNames) {
		this.statusNames = statusNames;
	}
	/*
	 * 查找数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#find(java.lang.Object)
	 */
	@Override
	protected List<User> find(UserInfo info) {
		 return this.userDao.findUsers(info);
	}
	/*
	 * 类型转换。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#changeModel(java.lang.Object)
	 */
	@Override
	protected UserInfo changeModel(User data) {
		if(data == null) return null;
		
		UserInfo info = new UserInfo(); 
		BeanUtils.copyProperties(data, info, new String[]{"password"});
		//解密密码。
		info.setPassword(this.passwordHelper.decryptAESPassword(data));
		
		if(info.getGender() != null) info.setGenderName(this.loadGenderName(info.getGender()));
		if(info.getStatus() != null) info.setStatusName(this.loadUserStatusName(info.getStatus()));
		return info;
	}
	/*
	 * 查询统计。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#total(java.lang.Object)
	 */
	@Override
	protected Long total(UserInfo info) {
		return this.userDao.total(info);
	}
	/*
	 * 更新数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#update(java.lang.Object)
	 */
	@Override
	public UserInfo update(UserInfo info) {
		if(info == null) return null;
		boolean isAdded = false;
		User  data = StringUtils.isEmpty(info.getId()) ?  null : this.userDao.load(User.class, info.getId());
		if(isAdded = (data == null)){
			if(StringUtils.isEmpty(info.getId())){
				info.setId(UUID.randomUUID().toString());
			}
			info.setCreateTime(new Date());
			data = new User();
		}
		info.setLastLoginTime(new Date());
		if(!isAdded)info.setCreateTime(data.getCreateTime());
		BeanUtils.copyProperties(info, data, new String[]{"password"});
		//加密密码。
		data.setPassword(this.passwordHelper.encryptAESPassword(info));
		//新增数据。
		if(isAdded) this.userDao.save(data);
		//性别名称。
		if(StringUtils.isEmpty(info.getGenderName()) && info.getGender() != null){
			info.setGenderName(this.loadGenderName(info.getGender()));
		}
		return info;
	}
	/*
	 * 删除数据。
	 * @see com.examw.netplatform.service.impl.BaseDataServiceImpl#delete(java.lang.String[])
	 */
	@Override
	public void delete(String[] ids) {
		 if(ids == null || ids.length == 0) return;
		 for(int i = 0; i  < ids.length; i++){
			 if(StringUtils.isEmpty(ids[i])) continue;
			 User data = this.userDao.load(User.class, ids[i]);
			 if(data != null) this.userDao.delete(data);
		 }
	}
	/*
	 * 加载用户状态名称。
	 * @see com.examw.netplatform.service.admin.IUserService#loadUserStatusName(int)
	 */
	@Override
	public String loadUserStatusName(Integer status) {
		if(this.statusNames == null || this.statusNames.size() == 0) return status.toString();
		return this.statusNames.get(status);
	}
	/*
	 * 加载性别名称
	 * @see com.examw.netplatform.service.admin.IUserService#loadGenderName(java.lang.Integer)
	 */
	@Override
	public String loadGenderName(Integer gender) {
		if(this.genderNames == null || this.genderNames.size() == 0) return gender.toString();
		return this.genderNames.get(gender);
	}
	/*
	 * 更新密码。
	 * @see com.examw.netplatform.service.admin.IUserService#changePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public void changePassword(String userId, String newPassword) {
		if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(newPassword)) return;
		User data = this.userDao.load(User.class, userId);
		if(data != null){
			UserInfo info = new UserInfo();
			BeanUtils.copyProperties(data, info, new String[]{"password"}); 
			info.setPassword(newPassword);
			data.setPassword(this.passwordHelper.encryptAESPassword(info));
		}
	}
	/*
	 * 根据账号查找用户。
	 * @see com.examw.netplatform.service.admin.IUserService#findByAccount(java.lang.String)
	 */
	@Override
	public User findByAccount(String account) {
		if(StringUtils.isEmpty(account)) return null;
		return this.userDao.findByAccount(account);
	}
}