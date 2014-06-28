package com.examw.collector.service;

import java.io.IOException;

/**
 * 远程数据代理接口。
 * @author yangyong.
 * @since 2014-06-26.
 */
public interface IRemoteDataProxy {
	/**
	 * 构造用于验证的加密字符串。
	 * @param parameters
	 * 参数集合。
	 * @return 加密字符串。
	 */
	String buildCheckCode(String[] parameters);
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
	/**
	 * 给合作客户传递用户注册、报名、充值、缴费等信息。
	 * @param cataId
	 * 验证内容：
	 * 1-注册
	 * 2-登录
	 * 3-选课
	 * 4-充值
	 * 5-扣费
	 * 6-学习
	 * @param orderCode
	 * 订单编号。
	 * @param lesson_code
	 * 课程编号。
	 * @param discount_code
	 * 套餐编号。
	 * @param part
	 * 讲。
	 * @param money
	 * 消费金额。
	 * @param card_num
	 * 学习卡号。
	 * @param card_pwd
	 * 学习卡密码。
	 * @param username
	 * 注册的用户名。
	 * @param password
	 * 注册的密码。
	 * @param name
	 * 姓名。
	 * @param email
	 * 注册邮件。
	 * @param province
	 * 所属地区。
	 * @param tel
	 * 注册手机和电话。
	 * @return
	 */
	String postUsers(Integer cataId,String orderCode, String[] lesson_code, String[] discount_code,String part, 
			String money,String card_num, String card_pwd,String username,String password, String name,String email,
			String province,String tel) throws IOException;
	/**
	 * 定制大屏播放器接口。
	 * @param videoId
	 * 课件ID（讲标示）。
	 * @param videoType
	 * 视频类别：
	 * 1-宣传片
	 * 2-试听
	 * 3-正式课件（默认）
	 * @param userName
	 * 注册的用户名。
	 * @return
	 */
	String loadVideo(String videoId, Integer videoType,String userName) throws IOException;
}