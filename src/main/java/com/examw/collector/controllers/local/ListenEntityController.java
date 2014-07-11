package com.examw.collector.controllers.local;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.RelateInfo;
import com.examw.collector.service.IListenEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 课节(实际)控制器
 * @author fengwei.
 * @since 2014年7月6日 上午8:53:09.
 */
@Controller
@RequestMapping("/admin/local/listen")
public class ListenEntityController {
	private static Logger logger  = Logger.getLogger(ListenEntityController.class);
	@Resource
	private IListenEntityService listenEntityService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(Model model) {
		return "local/listen_list";
	}

	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<RelateInfo> datagrid(RelateInfo info) {
		return this.listenEntityService.datagrid(info);
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(RelateInfo info){
		Json result = new Json();
		try {
			this.listenEntityService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化试听时发生异常", e);
		}
		return result;
	}
}
