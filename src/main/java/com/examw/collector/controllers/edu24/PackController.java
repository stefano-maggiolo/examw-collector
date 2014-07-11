package com.examw.collector.controllers.edu24;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IPackService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 套餐(副本)控制器
 * @author fengwei.
 * @since 2014年7月1日 上午10:50:03.
 */
@Controller
@RequestMapping("/admin/edu24/pack")
public class PackController {
	private static Logger logger  = Logger.getLogger(PackController.class);
	@Resource
	private IPackService packService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "remote/pack_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PackInfo> datagrid(PackInfo info){
		return this.packService.datagrid(info);
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(PackInfo info){
		Json result = new Json();
		try {
			this.packService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化课程时发生异常", e);
		}
		return result;
	}
}
