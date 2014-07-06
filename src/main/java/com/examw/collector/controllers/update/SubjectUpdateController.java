package com.examw.collector.controllers.update;

import java.util.ArrayList;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.controllers.edu24.CatalogController;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectEntityService;
import com.examw.collector.service.ISubjectService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 
 * @author fengwei.
 * @since 2014年7月6日 下午2:32:24.
 */
@Controller
@RequestMapping("/admin/update/subject")
public class SubjectUpdateController {
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	@Resource
	private ISubjectEntityService subjectEntityService;
	@Resource
	private ISubjectService subjectService;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "update/subject_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubjectInfo> datagrid(String action,SubjectInfo info){
		if(action == null || !action.equals("1"))
		{
			DataGrid<SubjectInfo> data = new DataGrid<SubjectInfo>();
			data.setRows(new ArrayList<SubjectInfo>());
			return data;
		}
		return this.subjectService.dataGridUpdate();
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestParam(value = "SubjectInfo[]")SubjectInfo[] subjects){
		Json data = new Json();
		logger.info("subjects.size="+subjects.length);
		for(SubjectInfo info:subjects){
			System.out.println(info.getCode());
		}
		return data;
	}
}
