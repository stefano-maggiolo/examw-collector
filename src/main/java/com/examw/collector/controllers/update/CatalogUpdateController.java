package com.examw.collector.controllers.update;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.controllers.edu24.CatalogController;
import com.examw.collector.model.CatalogInfo;
import com.examw.collector.service.ICatalogEntityService;
import com.examw.collector.service.ICatalogService;
import com.examw.model.DataGrid;

/**
 * 
 * @author fengwei.
 * @since 2014年7月6日 上午11:17:18.
 */
@Controller
@RequestMapping("/admin/update/catalog")
public class CatalogUpdateController {
	private static Logger logger  = Logger.getLogger(CatalogController.class);
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
		return this.catalogService.dataGridUpdate();
	}
	
}
