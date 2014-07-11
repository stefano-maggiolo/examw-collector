package com.examw.collector.controllers.update;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IGradeUpdateService;
import com.examw.collector.service.ISubClassService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 班级数据更新控制器
 * @author fengwei.
 * @since 2014年7月7日 下午4:43:11.
 */
@Controller
@RequestMapping("/admin/update/grade")
public class GradeUpdateController {
	private static Logger logger  = Logger.getLogger(GradeUpdateController.class);
	@Resource
	private IGradeUpdateService gradeUpdateService;
	@Resource
	private ISubClassService subClassService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "update/grade_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubClassInfo> datagrid(String action,SubClassInfo info){
		if(action == null || !action.equals("1"))
		{
			DataGrid<SubClassInfo> data = new DataGrid<SubClassInfo>();
			data.setRows(new ArrayList<SubClassInfo>());
			return data;
		}
		return this.subClassService.dataGridUpdate(info);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody List<SubClassInfo> subClasses){
		Json result = new Json();
		try {
			this.gradeUpdateService.update(subClasses);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
}
