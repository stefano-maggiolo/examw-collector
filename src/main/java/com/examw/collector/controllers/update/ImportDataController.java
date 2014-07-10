package com.examw.collector.controllers.update;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.service.IImportDataService;
import com.examw.model.Json;

/**
 * 整体数据导入控制器
 * @author fengwei.
 * @since 2014年7月10日 上午11:24:12.
 */
@Controller
@RequestMapping("/admin/import/data")
public class ImportDataController {
	private static Logger logger  = Logger.getLogger(ImportDataController.class);
	@Resource
	private IImportDataService importDataService;
	@RequestMapping(value={"","/list"}, method = RequestMethod.GET)
	public String list(Model model){
		return "update/import_list";
	}
	@RequestMapping(value = "/init", method = RequestMethod.POST)
	@ResponseBody
	public Json init(String id){
		Json result = new Json();
		try {
			String[] ids = id.split(",");
			this.importDataService.init(ids);
			result.setSuccess(true);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getMessage());
			logger.error("数据整体导入时发生异常", e);
		}
		return result;
	}
}
