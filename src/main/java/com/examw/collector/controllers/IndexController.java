package com.examw.collector.controllers;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.domain.Catalog;
import com.examw.collector.service.IDataServer;

/**
 * 
 * @author fengwei.
 * @since 2014年6月28日 上午9:32:12.
 */
@Controller
public class IndexController {
	@Resource
	private IDataServer dataServer;
	@RequestMapping(value = {"/default","/index","/",""}, method =  RequestMethod.GET)
	public String index(){
		return "index";
	}
	@RequestMapping(value = {"/admin/top"}, method =  RequestMethod.GET)
	public String top(){
		return "top";
	}
	/**
	 * 获取默认工作页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/admin/center", method = RequestMethod.GET)
	public String defaultWorkspace(Model model){
		return "Workspace";
	}
	@RequestMapping(value = "/catalog", method = RequestMethod.GET)
	public String catalog(){
		return "catalog";
	}
	@RequestMapping(value = "/catalogdata", method = RequestMethod.GET)
	@ResponseBody
	public List<Catalog> catalogTreeGrid(){
		return this.dataServer.loadCatalogs();
	}
}
