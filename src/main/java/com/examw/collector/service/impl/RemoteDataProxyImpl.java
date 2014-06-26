package com.examw.collector.service.impl;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger; 
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.service.IRemoteDataProxy;
import com.examw.collector.support.HttpUtil;
/**
 * 远程数据代理实现。
 * @author yangyong.
 * @since 2014-06-26.
 */
public class RemoteDataProxyImpl implements IRemoteDataProxy {
	private static Logger logger = Logger.getLogger(RemoteDataProxyImpl.class);
	private String lessonUrl,sid,key;
	/**
	 * 设置服务器URL。
	 * @param url
	 */
	public void setLessonUrl(String lessonUrl) {
		this.lessonUrl = lessonUrl;
	}
	/**
	 * 设置客户ID。
	 * @param sid
	 * 客户ID。
	 */
	public void setSid(String sid) {
		this.sid = sid;
	}
	/**
	 * 设置私钥。
	 * @param key
	 * 私钥。
	 */
	public void setKey(String key) {
		this.key = key;
	}
	/*
	 * 加载课程数据。
	 * @see com.examw.collector.service.IRemoteDataProxy#load(java.util.Map)
	 */
	@Override
	public String loadLesson(Integer cataId,String lesson_type_code, String lesson_code,String class_code) throws IOException{
		logger.info("获取课程数据...");
		String time = this.builderTime(new Date());
		Map<String, String> parameters = new HashMap<String, String>();
		parameters.put("Cataid", cataId == null ? "" : cataId.toString());
		parameters.put("lesson_type_code", StringUtils.isEmpty(lesson_type_code) ? "" : lesson_type_code);
		parameters.put("lesson_code", StringUtils.isEmpty(lesson_code) ? "" : lesson_code);
		parameters.put("class_code", StringUtils.isEmpty(class_code) ? "" : class_code);
		parameters.put("Time", time);
		StringBuilder post = new StringBuilder(this.buildCheckCode(parameters));
		post.append("$").append(this.sid)
		       .append("$").append(cataId == null ? "" : cataId.toString())
		       .append("$").append(StringUtils.isEmpty(lesson_type_code) ? "" : lesson_type_code)
		       .append("$").append(StringUtils.isEmpty(class_code) ? "" : class_code)
		       .append("$").append(time);
		logger.info("参数：" + post.toString());
		String url = String.format(this.lessonUrl, java.net.URLEncoder.encode(post.toString(), "UTF-8"));
		logger.info("url:" + url);
		return HttpUtil.httpRequest(url, "GET",null);
	}
	/**
	 * 构造用于验证的加密字符串。
	 * @param time
	 * 时间。
	 * @param parameters
	 * 参数集合。
	 * @return
	 */
	private String buildCheckCode(Map<String, String> parameters){
		logger.info("构造用于验证的加密字符串...");
		StringBuilder builder = new StringBuilder(this.sid);
		if(parameters != null && parameters.size() > 0){
			for(String value : parameters.values()){
				builder.append(StringUtils.isEmpty(value) ? "" : value);
			}
		}
		builder.append(this.key);
		logger.info("加密前字符串:" + builder.toString());
		String checkCode = DigestUtils.md5DigestAsHex(builder.toString().getBytes());
		logger.info("加密字符串:" + checkCode);
		return checkCode;
	}
	/**
	 * 构建传递时间。
	 * @param time
	 * @return
	 */
	private String builderTime(Date time){
		return new SimpleDateFormat("YYYY MM dd HH mm ss").format(time);
	}
}