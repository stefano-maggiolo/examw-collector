package com.examw.collector.controllers.security;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.domain.User;
import com.examw.collector.model.UserInfo;
import com.examw.collector.service.IUserService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 用户控制器
 * @author fengwei.
 * @since 2014年7月14日 上午8:22:57.
 */
@Controller
@RequestMapping(value = "/admin/security/user")
public class UserController {
	private static Logger logger = Logger.getLogger(UserController.class);
	
	@Resource
	private IUserService userService;
	
	/**
	 * 获取列表页面。
	 * @return
	 */
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		model.addAttribute("STATUS_ENABLED", this.userService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.userService.loadUserStatusName(User.STATUS_DISABLE));
		return "security/user_list";
	}
	/**
	 * 获取编辑页面。
	 * @param agencyId
	 * 所属培训机构。
	 * @param model
	 * 数据绑定。
	 * @return
	 * 编辑页面地址。
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(String agencyId,Model model){
		
		model.addAttribute("STATUS_ENABLED", this.userService.loadUserStatusName(User.STATUS_ENABLED));
		model.addAttribute("STATUS_DISABLE", this.userService.loadUserStatusName(User.STATUS_DISABLE));
		
		model.addAttribute("GENDER_MALE", this.userService.loadGenderName(User.GENDER_MALE));
		model.addAttribute("GENDER_FEMALE", this.userService.loadGenderName(User.GENDER_FEMALE));
		
		return "security/user_edit";
	}
	/**
	 * 查询数据。
	 * @return
	 */
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<UserInfo> datagrid(UserInfo info){
		return this.userService.datagrid(info);
	}
	/**
	 * 更新数据。
	 * @param info
	 * 更新源数据。
	 * @return
	 * 更新后数据。
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(UserInfo info, HttpServletRequest request){
		Json result = new Json();
		try {
			 info.setLastLoginIP(request.getRemoteAddr());
			 result.setData(this.userService.update(info));
			 result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新用户数据发生异常", e);
		}
		return result;
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
			this.userService.delete(id.split("\\|"));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("删除数据["+id+"]时发生异常:", e);
		}
		return result;
	}
}