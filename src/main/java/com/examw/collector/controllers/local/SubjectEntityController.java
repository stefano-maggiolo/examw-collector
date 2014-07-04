package com.examw.collector.controllers.local;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;

/**
 * 
 * @author fengwei.
 * @since 2014年7月3日 上午11:00:11.
 */
@Controller
@RequestMapping("/admin/local/subject")
public class SubjectEntityController {
	private static Logger logger  = Logger.getLogger(SubjectEntityController.class);
	@Resource
	private ISubjectEntityService subjectEntityService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(Model model) {
		return "local/subject_list";
	}

	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubjectInfo> datagrid(SubjectInfo info) {
		return this.subjectEntityService.datagrid(info);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> tree(SubjectInfo info) {
		List<TreeNode> result = new ArrayList<>();
		if(info==null||info.getCatalogId()==null) return result;
		List<SubjectInfo> list = this.subjectEntityService.datagrid(info).getRows();
		if (list != null && list.size() > 0) {
			for (final SubjectInfo s : list) {
				if (s == null)
					continue;
				result.add(new TreeNode() {
					private static final long serialVersionUID = 1L;
					@Override
					public String getId() {
						return s.getCode();
					}
					@Override
					public String getText() {
						return s.getName()+"("+s.getCode()+")";
					}
				});
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(SubjectInfo info){
		Json result = new Json();
		try {
			this.subjectEntityService.init(info);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("初始化科目时发生异常", e);
		}
		return result;
	}
}
