package com.examw.collector.controllers.local;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.TeacherInfo;
import com.examw.collector.service.ITeacherEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 老师控制器
 * @author fengwei.
 * @since 2014年7月9日 下午2:29:18.
 */
@Controller
@RequestMapping("/admin/local/teacher")
public class TeacherEntityController {
	private static Logger logger  = Logger.getLogger(TeacherEntityController.class);
	@Resource
	private ITeacherEntityService teacherEntityService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(Model model) {
		return "local/teacher_list";
	}

	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<TeacherInfo> datagrid(TeacherInfo info) {
		return this.teacherEntityService.datagrid(info);
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(TeacherInfo info){
		Json result = new Json();
		try {
			this.teacherEntityService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化老师时发生异常", e);
		}
		return result;
	}
}
