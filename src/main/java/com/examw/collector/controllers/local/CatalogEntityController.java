package com.examw.collector.controllers.local;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.aware.IUserAware;
import com.examw.collector.controllers.edu24.CatalogController;
import com.examw.collector.model.local.CatalogEntityInfo;
import com.examw.collector.service.ICatalogEntityService;
import com.examw.model.DataGrid;
import com.examw.model.Json;
import com.examw.model.TreeNode;

/**
 * 课程分类(实际)控制器
 * @author fengwei.
 * @since 2014年7月2日 上午10:09:17.
 */
@Controller
@RequestMapping("/admin/local/catalog")
public class CatalogEntityController implements IUserAware{
	private static Logger logger  = Logger.getLogger(CatalogController.class);
	private String account;
	@Resource
	private ICatalogEntityService catalogEntityService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "local/catalog_list";
	}
	@RequestMapping(value="/datagrid", method = RequestMethod.POST)
	@ResponseBody
	public DataGrid<CatalogEntityInfo> datagrid(CatalogEntityInfo info){
		return this.catalogEntityService.datagrid(info);
	}
	
	@RequestMapping(value="edit", method = RequestMethod.GET)
	public String edit(Model model){
		return "local/catalog_update_code";
	}
	
	@RequestMapping(value="/update", method = RequestMethod.POST)
	@ResponseBody
	public Json update(CatalogEntityInfo info){
		Json result = new Json();
		try {
			result.setData(this.catalogEntityService.update(info,account));
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("更新考试类型数据发生异常", e);
		}
		return result;
	}
	
	/**
	 * 考试类别树结构数据。
	 * @return
	 */
	@RequestMapping(value = "/tree", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public List<TreeNode> tree(){
		return this.catalogEntityService.loadAllCatalogs();
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
