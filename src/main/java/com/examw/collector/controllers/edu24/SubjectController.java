package com.examw.collector.controllers.edu24;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectService;
import com.examw.model.DataGrid;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 下午3:52:00.
 */
@Controller
@RequestMapping("/admin/edu24/subject")
public class SubjectController {
	@Resource
	private ISubjectService subjectService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "remote/subject_list";
	}
	
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubjectInfo> datagrid(SubjectInfo info){
		return this.subjectService.datagrid(info);
	}
}
