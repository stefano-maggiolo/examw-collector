package com.examw.collector.controllers.edu24;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.RelateInfo;
import com.examw.collector.service.IRelateService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 
 * @author fengwei.
 * @since 2014年7月1日 上午9:35:45.
 */
@Controller
@RequestMapping("/admin/edu24/relate")
public class RelateController {
	private static Logger logger  = Logger.getLogger(RelateController.class);
	@Resource
	private IRelateService relateService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "remote/relate_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RelateInfo> datagrid(RelateInfo info){
		return this.relateService.datagrid(info);
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(RelateInfo info){
		Json result = new Json();
		try {
			this.relateService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化课程时发生异常", e);
		}
		return result;
	}
}
