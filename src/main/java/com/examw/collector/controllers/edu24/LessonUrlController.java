package com.examw.collector.controllers.edu24;

import java.io.IOException;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.examw.collector.service.IRemoteDataProxy;
import com.examw.collector.service.impl.DataServerImpl;

/**
 * 获取环球数据URL的控制器
 * @author fengwei.
 * @since 2014年9月16日 上午8:50:47.
 */
@Controller
@RequestMapping("/admin/edu24/url")
public class LessonUrlController {
	private static Logger logger = Logger.getLogger(DataServerImpl.class);
	@Resource 
	private IRemoteDataProxy remoteDataProxy;
	/* 获取内容：
	 * 1=课程类别；2=班别；3=讲；4=套餐 ;20=包含直播班
	 * */
	private static final int CATALOG = 1,SUBCLASS = 20,RELATE = 3,PACK = 4;
	/**
	 * 环球课程分类数据URL
	 * @return
	 */
	@RequestMapping(value = "/catalog", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String catalogUrl(){
		logger.debug("获取环球课程分类数据URL...");
		try {
			return this.remoteDataProxy.loadLessonUrl(CATALOG, null, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取一个类别下所有的班级信息
	 * this.remoteDataProxy.loadLesson(20, lesson_type_code,null, null);
	 * @return
	 */
	@RequestMapping(value = "/subclass/{lesson_type_code}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String subclassUrl(@PathVariable String lesson_type_code){
		logger.debug("获取一个类别下所有的班级数据URL...");
		try {
			return this.remoteDataProxy.loadLessonUrl(SUBCLASS, lesson_type_code, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * String xml = this.remoteDataProxy.loadLesson(3, null, null,
					class_code.replace(ID_PREFIX, ""));	//code做减e处理
	 */
	@RequestMapping(value = "/relate/{class_code}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String relateUrl(@PathVariable String class_code){
		logger.debug("获取环球课程分类数据URL...");
		try {
			class_code = class_code.replace(DataServerImpl.ID_PREFIX, "");
			return this.remoteDataProxy.loadLessonUrl(RELATE, null, null, class_code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping(value = "/pack/{lesson_type_code}", method = {RequestMethod.GET,RequestMethod.POST})
	@ResponseBody
	public String packUrl(@PathVariable String lesson_type_code){
		logger.debug("获取环球一个类别下所有套餐数据URL...");
		try {
			return this.remoteDataProxy.loadLessonUrl(PACK, lesson_type_code, null, null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
