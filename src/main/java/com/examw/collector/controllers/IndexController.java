package com.examw.collector.controllers;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.examw.collector.service.IMenuService;

/**
 * 
 * @author fengwei.
 * @since 2014年6月28日 上午9:32:12.
 */
@Controller
@RequestMapping(value={"/admin"})
public class IndexController {
	@Resource
	private IMenuService menuService;
	/**
	 * 获取首页。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = {"","index","/"}, method = RequestMethod.GET)
	public String index(Model model){ 
		model.addAttribute("systemName", this.menuService.loadSystemName());
		return "index";
	}
	/**
	 * 获取顶部
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "top", method = RequestMethod.GET)
	public String top(Model model){
		return "top";
	}
	/**
	 * 获取左边
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "left", method = RequestMethod.GET)
	public String left(Model model){ 
		model.addAttribute("modules", this.menuService.loadModules());
		return "left";
	}
	/**
	 * 获取默认工作页面。
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "center", method = RequestMethod.GET)
	public String defaultWorkspace(Model model){
		return "Workspace";
	}
}
