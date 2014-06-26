package com.examw.collector.service;

import java.io.IOException;

/**
 * 远程数据代理接口。
 * @author yangyong.
 * @since 2014-06-26.
 */
public interface IRemoteDataProxy {
	/**
	 * 加载课程数据。
	 * @param cataId
	 * 获取内容：
	 * 1－课程类别
	 * 2-班别
	 * 3-讲义
	 * 4-套餐
	 * @param lesson_type_code
	 * 课程小类的编码。
	 * @param lesson_code
	 * 科目的编码。
	 * @param class_code
	 * 班别编码。
	 * @return
	 */
	String loadLesson(Integer cataId,String lesson_type_code, String lesson_code,String class_code)  throws IOException;
}