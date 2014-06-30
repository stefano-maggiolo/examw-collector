package com.examw.collector.controllers.edu24;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.CatalogInfo;
import com.examw.collector.service.ICatalogService;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午10:40:54.
 */
@Controller
@RequestMapping("/admin/edu24/catalog")
public class CatalogController {
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	@Resource
	private ICatalogService catalogService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "remote/catalog_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<CatalogInfo> datagrid(CatalogInfo info){
		return this.catalogService.datagrid(info);
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(){
		Json result = new Json();
		try {
			this.catalogService.init();
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化课程时发生异常", e);
		}
		return result;
	}
	/**
	 * 考试类别树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> tree(){
		return this.catalogService.loadAllCatalogs();
	}
}
