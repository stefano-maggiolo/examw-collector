package com.examw.collector.service;

import java.util.List;

import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.SubClass;

/**
 * 数据服务接口。
 * @author yangyong.
 * @since 2014-06-26.
 */
public interface IDataServer {
	/**
	 * 加载课程类别集合。
	 * @return
	 */
	List<Catalog> loadCatalogs();
	/**
	 * 获取单个科目中班别信息。
	 * @param lesson_type_code
	 * 课程小类编码。
	 * @param lesson_code
	 * 科目编码。
	 * @return
	 */
	List<SubClass> loadClasses(String lesson_type_code,String lesson_code);
}