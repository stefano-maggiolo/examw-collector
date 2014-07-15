package com.examw.collector.controllers.security;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.model.LoginLogInfo;
import com.examw.collector.service.ILoginLogService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 登录日志控制器
 * @author fengwei.
 * @since 2014年7月14日 上午8:30:33.
 */
@Controller
@RequestMapping(value = "/admin/security/loginlog")
public class LoginLogController {
	private static Logger logger = Logger.getLogger(LoginLogController.class);
	@Resource
	private ILoginLogService loginLogService;
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "security/log_list";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<LoginLogInfo> datagrid(LoginLogInfo info){
		return this.loginLogService.datagrid(info);
	}
	/**
	 * 删除数据。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delete", method = RequestMethod.POST)
	@ResponseBody
	public Json delete(String id){
		Json result = new Json();
		try {
			this.loginLogService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}
