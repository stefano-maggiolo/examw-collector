package com.examw.collector.controllers.edu24;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.ISubClassService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 班级(副本)控制器
 * @author fengwei.
 * @since 2014年6月30日 下午5:32:33.
 */
@Controller
@RequestMapping("/admin/edu24/subclass")
public class SubClassController {
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	@Resource
	private ISubClassService subClassService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "remote/subclass_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubClassInfo> datagrid(SubClassInfo info){
		return this.subClassService.datagrid(info);
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(SubClassInfo info){
		Json result = new Json();
		try {
			this.subClassService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化课程时发生异常", e);
		}
		return result;
	}
	
}
