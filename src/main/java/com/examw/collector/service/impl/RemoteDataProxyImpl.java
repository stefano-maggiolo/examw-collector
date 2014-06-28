package com.examw.collector.service.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger; 
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.service.IRemoteDataProxy;
import com.examw.collector.support.HttpUtil;
import com.examw.utils.StringUtil;
/**
 * 远程数据代理实现。
 * @author yangyong.
 * @since 2014-06-26.
 */
public class RemoteDataProxyImpl implements IRemoteDataProxy {
	private static Logger logger = Logger.getLogger(RemoteDataProxyImpl.class);
	private String lessonUrl,userUrl,sid,key;
	/**
	 * 设置服务器URL。
	 * @param url
	 */
	public void setLessonUrl(String lessonUrl) {
		this.lessonUrl = lessonUrl;
	}
	/**
	 * 设置用户操作接口URL。
	 * @param userUrl
	 */
	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
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
		String post = this.buildPostUrl(new String[]{(cataId == null ? "" : cataId.toString()), 
																			 lesson_type_code, 
																			 lesson_code, 
																			 class_code, 
																			 this.builderTime(new Date())});
		String url = String.format(this.lessonUrl,post);
		logger.info("url:" + url);
		String xml = HttpUtil.httpRequest(url, "GET", null,"gbk");
		if(StringUtils.isEmpty(xml)) return null;
		 int index =	xml.indexOf("?>");
		 if(index > 0){
			 xml = xml.substring(index + 2);
		 }
		return xml;
	}
	/*
	 * 给合作客户传递用户注册、报名、充值、缴费等信息。
	 * @see com.examw.collector.service.IRemoteDataProxy#postUsers(java.lang.Integer, java.lang.String, java.lang.String[], java.lang.String[], java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public String postUsers(Integer cataId, String orderCode,
			String[] lesson_code, String[] discount_code, String part,
			String money, String card_num, String card_pwd, String username,
			String password, String name, String email, String province,
			String tel) throws IOException {
			logger.info("给合作客户传递用户注册、报名、充值、缴费等信息...");
			String post = this.buildPostUrl(new String[]{(cataId == null ? "" : cataId.toString()), 
																				 orderCode, 
																				 StringUtil.join(lesson_code, ','), 
																				 StringUtil.join(discount_code, ','),
																				 part,
																				 money,
																				 card_num,
																				 card_pwd,
																				 username,
																				 password,
																				 name,
																				 email,
																				 province,
																				 tel,
																				 this.builderTime(new Date())});
			String url = String.format(this.userUrl,post);
			logger.info("url:" + url);
			String xml = HttpUtil.httpRequest(url, "GET", null,"gbk");
			if(StringUtils.isEmpty(xml)) return null;
			 int index =	xml.indexOf("?>");
			 if(index > 0){
				 xml = xml.substring(index + 2);
			 }
			return xml;
	}
	/*
	 * 定制大屏播放器接口。
	 * @see com.examw.collector.service.IRemoteDataProxy#loadVideo(java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public String loadVideo(String videoId,Integer videoType, String userName) throws IOException {
		logger.info("定制大屏播放器接口...");
		String post = this.buildPostUrl(new String[]{videoId, 
																			(videoType == null ? "" : videoType.toString()), 
																			userName,
																			 this.builderTime(new Date())});
		String url = String.format(this.userUrl,post);
		logger.info("url:" + url);
		String xml = HttpUtil.httpRequest(url, "GET", null,"gbk");
		if(StringUtils.isEmpty(xml)) return null;
		 int index =	xml.indexOf("?>");
		 if(index > 0){
			 xml = xml.substring(index + 2);
		 }
		return xml;
	}
	/**
	 * 构建提交数据URL。
	 * @param parameters
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	private String buildPostUrl(String[] parameters) throws UnsupportedEncodingException{
		logger.info("构建提交数据URL...");
		StringBuilder post = new StringBuilder(this.buildCheckCode(parameters));
		post.append("$").append(this.sid);
		for(int i = 0; i < parameters.length; i++){
			post.append("$").append(StringUtils.isEmpty(parameters[i]) ? "" : parameters[i]);
		}
		String postUrl = post.toString();
		logger.info("post:" + postUrl);
		postUrl = URLEncoder.encode(postUrl, "UTF-8");
		return postUrl;
	}
	/**
	 * 构造用于验证的加密字符串。
	 * @param parameters
	 * 参数集合。
	 * @return 加密字符串。
	 */
	public String buildCheckCode(String[] parameters){
		logger.info("构造用于验证的加密字符串...");
		StringBuilder builder = new StringBuilder(this.sid);
		if(parameters != null && parameters.length > 0){
			for(int i = 0; i < parameters.length; i++){
				if(!StringUtils.isEmpty(parameters[i])){
					builder.append(parameters[i]);
				}
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
		return new SimpleDateFormat("YYYYMMddHHmmss").format(time);
	}
}