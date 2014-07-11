package com.examw.collector.controllers.edu24;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectService;
import com.examw.model.DataGrid;
import com.examw.model.TreeNode;

/**
 * 科目(副本)控制器
 * @author fengwei.
 * @since 2014年6月30日 下午3:52:00.
 */
@Controller
@RequestMapping("/admin/edu24/subject")
public class SubjectController {
	@Resource
	private ISubjectService subjectService;

	@RequestMapping(value = { "", "/list" }, method = RequestMethod.GET)
	public String list(Model model) {
		return "remote/subject_list";
	}

	@RequestMapping(value = "/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<SubjectInfo> datagrid(SubjectInfo info) {
		return this.subjectService.datagrid(info);
	}

	@RequestMapping(value = "/tree", method = RequestMethod.POST)
	@ResponseBody
	public List<TreeNode> tree(SubjectInfo info) {
		List<TreeNode> result = new ArrayList<>();
		if(info==null||info.getCatalogId()==null) return result;
		List<SubjectInfo> list = this.subjectService.datagrid(info).getRows();
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
}
