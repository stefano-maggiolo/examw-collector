package com.examw.collector.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import com.examw.collector.domain.system.Menu;
import com.examw.collector.model.system.MenuInfo;
import com.examw.collector.service.IMenuService;
import com.examw.configuration.ModuleDefineCollection;
import com.examw.configuration.ModuleSystem;
import com.examw.configuration.ModuleSystemCollection;
/**
 * 菜单服务。
 * @author yangyong
 * @since 2014-04-28.
 */
public class MenuServiceImpl  implements IMenuService {
	private static Logger logger = Logger.getLogger(MenuServiceImpl.class);
	private String menuFile,systemId;
	private static Map<String, ModuleSystem> mapSystemCache = Collections.synchronizedMap(new HashMap<String,ModuleSystem>());
	/**
	 * 设置菜单文件。
	 * @param menuFile
	 * 菜单文件。
	 */
	public void setMenuFile(String menuFile) {
		this.menuFile = menuFile;
	}
	/**
	 * 设置菜单文件中系统ID。
	 * @param systemId
	 * 系统ID。
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}
	/**
	 * 加载文件解析为对象集合。
	 * @return
	 * 对象集合。
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 * @throws SAXException 
	 */
	private synchronized ModuleSystemCollection loadFileToParse(){
		if(StringUtils.isEmpty(this.menuFile)){
			logger.info("菜单文件为空！");
			return null;
		}
		Resource rs = new ClassPathResource(this.menuFile, ClassUtils.getDefaultClassLoader());
		if(rs != null) {
			try {
				ModuleSystemCollection collection = ModuleSystemCollection.parse(rs.getInputStream());
				if(collection != null && collection.size() > 0) return collection;
			} catch (SAXException | IOException | ParserConfigurationException e) {
				logger.error("加载文件解析为对象集合发生异常:", e);
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 加载系统菜单数据。
	 * @return
	 */
	@Override
	public synchronized ModuleSystem loadSystem() {
		if(StringUtils.isEmpty(this.systemId)){
			logger.info("系统ID为空！");
			return null;
		}
		ModuleSystem ms = mapSystemCache.get(this.systemId);
		if(ms == null){
			ModuleSystemCollection collection = this.loadFileToParse();
			if(collection != null && collection.size() > 0){
				 for(ModuleSystem system : collection){
					 if(system != null && system.getId().equalsIgnoreCase(this.systemId)){
						 ms = system;
						 break;
					 }
				 }
				 if(ms != null){
					 mapSystemCache.put(this.systemId, ms);
				 }
			}
		}
		return ms;
	}
	/**
	 * 数据类型转换。
	 * @param data
	 * 实体对象。
	 * @return
	 * 结果数据。
	 */
	protected MenuInfo changeModel(Menu data) {
		if(data == null) return null;
		MenuInfo info = new MenuInfo();
		BeanUtils.copyProperties(data, info, new String[] {"children"});
		if(data.getChildren() != null && data.getChildren().size() > 0){
			List<MenuInfo> children = new ArrayList<>();
			for(Menu m : data.getChildren()){
				MenuInfo c = this.changeModel(m);
				if(c != null){
					c.setPid(data.getId());
					children.add(c);
				}
			}
			info.setChildren(children);
		}
		return info;
	}
	/*
	 * 加载系统名称。
	 * @see com.examw.netplatform.service.admin.IMenuService#loadSystemName()
	 */
	@Override
	public String loadSystemName() {
		ModuleSystem ms = this.loadSystem();
		return ms == null ? null : ms.getName();
	}
	/*
	 *加载系统模块集合。
	 * @see com.examw.netplatform.service.admin.IMenuService#loadModules()
	 */
	@Override
	public ModuleDefineCollection loadModules() {
		ModuleSystem ms = this.loadSystem();
		return ms == null ? null : ms.getModules();
	}
}