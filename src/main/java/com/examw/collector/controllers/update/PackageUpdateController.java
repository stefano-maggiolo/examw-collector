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
import com.examw.collector.model.PackInfo;
import com.examw.collector.service.IPackService;
import com.examw.collector.service.IPackageUpdateService;
import com.examw.model.DataGrid;
import com.examw.model.Json;

/**
 * 套餐数据更新控制器
 * @author fengwei.
 * @since 2014年7月8日 上午11:43:46.
 */
@Controller
@RequestMapping("/admin/update/pack")
public class PackageUpdateController implements IUserAware{
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	@Resource
	private IPackageUpdateService packageUpdateService;
	@Resource
	private IPackService packService;
	private String account;
	
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "update/pack_list_2";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PackInfo> datagrid(String action,PackInfo info){
		if(action == null || !action.equals("1"))
		{
			DataGrid<PackInfo> data = new DataGrid<PackInfo>();
			data.setRows(new ArrayList<PackInfo>());
			return data;
		}
		return this.packService.dataGridUpdate(info,account);
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(@RequestBody List<PackInfo> packs){
		Json result = new Json();
		try {
			result.setData(this.packageUpdateService.update(packs,account));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	/**
	 * 一键直接更新
	 * @return
	 */
	@RequestMapping(value="/onekeyupdate", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<PackInfo> oneKeyUpdate(){
		return this.packageUpdateService.dataGridUpdate(account);
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
