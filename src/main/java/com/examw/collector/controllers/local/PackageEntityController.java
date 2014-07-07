package com.examw.collector.controllers.local;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.PackInfo;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IPackageEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 本地套餐控制器
 * @author fengwei.
 * @since 2014年7月7日 上午9:27:28.
 */
@Controller
@RequestMapping("/admin/local/pack")
public class PackageEntityController {
	private static Logger logger  = Logger.getLogger(GradeEntityController.class);
	@Resource
	private IPackageEntityService packageEntityService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "local/pack_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PackInfo> datagrid(PackInfo info){
		return this.packageEntityService.datagrid(info);
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(PackInfo info){
		Json result = new Json();
		try {
			this.packageEntityService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化课程时发生异常", e);
		}
		return result;
	}
}
