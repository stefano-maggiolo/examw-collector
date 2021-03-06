package com.examw.collector.controllers.update;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.collector.controllers.edu24.CatalogController;
import com.examw.collector.model.CatalogInfo;
import com.examw.collector.service.ICatalogEntityService;
import com.examw.collector.service.ICatalogService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 课程分类数据更新控制器
 * @author fengwei.
 * @since 2014年7月6日 上午11:17:18.
 */
@Controller
@RequestMapping("/admin/update/catalog")
public class CatalogUpdateController implements IUserAware{
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	private String account;
	@Resource
	private ICatalogEntityService catalogEntityService;
	@Resource
	private ICatalogService catalogService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "update/catalog_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<CatalogInfo> datagrid(CatalogInfo info){
		return this.catalogService.dataGridUpdate(account);
	}
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody List<CatalogInfo> catalogs){
		Json result = new Json();
		try {
			this.catalogService.update(catalogs,account);
//			this.catalogEntityService.update(catalogs);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	@Override
	public void setUserId(String userId) {
	}
	@Override
	public void setUserName(String userName) {
	}
	@Override
	public void setUserNickName(String userNickName) {
		this.account = userNickName;
	}
}
