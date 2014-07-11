package com.examw.collector.controllers.local;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IGradeEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 班级(实际)控制器
 * @author fengwei.
 * @since 2014年7月3日 下午3:11:32.
 */
@Controller
@RequestMapping("/admin/local/grade")
public class GradeEntityController {
	private static Logger logger  = Logger.getLogger(GradeEntityController.class);
	@Resource
	private IGradeEntityService gradeEntityService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "local/grade_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubClassInfo> datagrid(SubClassInfo info){
		return this.gradeEntityService.datagrid(info);
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(SubClassInfo info){
		Json result = new Json();
		try {
			this.gradeEntityService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化课程时发生异常", e);
		}
		return result;
	}
}
