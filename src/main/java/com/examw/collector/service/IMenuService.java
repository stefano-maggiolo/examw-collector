package com.examw.collector.service;

import com.examw.configuration.ModuleDefineCollection;
import com.examw.configuration.ModuleSystem;

/**
 * 菜单服务接口。
 * @author yangyong.
 * @since 2014-04-26.
 */
public interface IMenuService {
	/**
	 * 加载系统信息。
	 * @return
	 * 系统信息对象。
	 */
	ModuleSystem loadSystem();
	/**
	 * 加载系统名称。
	 * @return
	 * 系统名称。
	 */
	String loadSystemName();
	/**
	 * 加载系统模块集合。
	 * @return
	 * 系统模块集合。
	 */
	ModuleDefineCollection loadModules();
}