package com.examw.collector.controllers.update;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.controllers.edu24.CatalogController;
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IPackService;
import com.examw.collector.service.IPackageEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 
 * @author fengwei.
 * @since 2014年7月8日 上午11:43:46.
 */
@Controller
@RequestMapping("/admin/update/pack")
public class PackageUpdateController {
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	@Resource
	private IPackageEntityService packageEntityService;
	@Resource
	private IPackService packService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "update/pack_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PackInfo> datagrid(String action,PackInfo info){
		if(action == null || !action.equals("1"))
		{
			DataGrid<PackInfo> data = new DataGrid<PackInfo>();
			data.setRows(new ArrayList<PackInfo>());
			return data;
		}
		return this.packService.dataGridUpdate(info);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody List<PackInfo> packs){
		Json result = new Json();
		try {
			this.packService.update(packs);
			this.packageEntityService.update(packs);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
}
