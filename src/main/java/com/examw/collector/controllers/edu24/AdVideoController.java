package com.examw.collector.controllers.edu24;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.AdVideoInfo;
import com.examw.collector.service.IAdVideoService;
import com.examw.model.DataGrid;

/**
 * 广告服务控制器
 * @author fengwei.
 * @since 2014年7月1日 上午10:50:13.
 */
@Controller
@RequestMapping("/admin/edu24/advideo")
public class AdVideoController {
	@Resource
	private IAdVideoService adVideoService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "remote/advideo_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<AdVideoInfo> datagrid(AdVideoInfo info){
		return this.adVideoService.datagrid(info);
	}
}	
