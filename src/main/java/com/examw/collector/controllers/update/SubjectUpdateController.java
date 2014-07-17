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

import com.examw.aware.IUserAware;
import com.examw.collector.controllers.edu24.CatalogController;
import com.examw.collector.model.SubjectInfo;
import com.examw.collector.service.ISubjectService;
import com.examw.collector.service.ISubjectUpdateService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 科目数据更新控制器
 * @author fengwei.
 * @since 2014年7月6日 下午2:32:24.
 */
@Controller
@RequestMapping("/admin/update/subject")
public class SubjectUpdateController implements IUserAware{
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	@Resource
	private ISubjectUpdateService subjectUpdateService;
	@Resource
	private ISubjectService subjectService;
	private String account;
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
		return this.subjectService.dataGridUpdate(account);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody List<SubjectInfo> subjects){
		Json result = new Json();
		try {
			result.setData(this.subjectUpdateService.update(subjects,account));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	@Override
	public void setUserId(String userId) {
	}
	@Override
	public void setUserName(String userName) {
	}
	@Override
	public void setUserNickName(String userNickName) {
		this.account = userNickName;
	}
}
