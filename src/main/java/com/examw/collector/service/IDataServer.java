package com.examw.collector.service;

import java.util.List;

import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.Relate;
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
	/**
	 * 获取课时（讲）集合。
	 * @param class_code
	 * @return
	 */
	List<Relate> loadRelates(String class_code);
	/**
	 * 获取单个科目中套餐的信息接口。
	 * @param lesson_type_code
	 * 课程小类编码。
	 * @param lesson_code
	 * 科目编码。
	 * @return
	 */
	List<Pack> loadPacks(String lesson_type_code, String lesson_code);
	/**
	 * 注册。
	 * @param userName
	 * 用户名。
	 * @param password
	 * 密码。
	 * @param name
	 * 姓名。
	 * @param email
	 * 注册邮件。
	 * @param province
	 * 所属地区。
	 * @param tel
	 * 注册手机和电话。
	 * @return 注册结果。
	 */
	boolean register(String userName,String password,String name,String email,String province,String tel) throws Exception;
	/**
	 * 登录。
	 * @param userName
	 * 用户名。
	 * @param password
	 * 密码。
	 * @return
	 */
	boolean login(String userName,String password) throws Exception;
	/**
	 * 选课。
	 * @param userName
	 * 用户名。
	 * @param password
	 * 密码。
	 * @param type
	 * 类型：1-课程编号，2-套餐编号。
	 * @param codes
	 * @return
	 * @throws Exception
	 */
	boolean choiceCourse(String userName,String password,Integer type,String[] codes) throws Exception;
	/**
	 * 充值。
	 * @param userName
	 * 用户名。
	 * @param password
	 * 密码。
	 * @param money
	 * 消费金额。
	 * @param cardnum
	 * 学习卡号
	 * @param cardpwd
	 * 学习卡密码
	 * @return
	 * @throws Exception
	 */
	boolean recharge(String userName,String password,Integer money,String cardnum,String cardpwd) throws Exception;
	/**
	 * 扣费。
	 * @param userName
	 * 用户名。
	 * @param password
	 * 密码。
	 * @param orderCode
	 * 订单编号。
	 * @param money
	 * 消费金额。
	 * @return
	 * @throws Exception
	 */
	boolean payment(String userName,String password,String orderCode,Integer money) throws Exception;
	/**
	 * 学习。
	 * @param userName
	 * 用户名。
	 * @param password
	 * 密码。
	 * @param lesson_code
	 * 课程编号。
	 * @param part
	 * 课时编号（讲）。
	 * @return
	 * @throws Exception
	 */
	boolean study(String userName,String password,String lesson_code,String part) throws Exception;
	/**
	 * 定制大屏播放器.
	 * @param userName
	 * 用户名。
	 * @param type
	 * 视频类别：1=宣传片；2=试听；3=正式课件(默认)
	 * @param videoId
	 * 课件ID（讲标识）
	 * @return 视频地址。
	 * Error1 - URL参数不完整.
	 * Error2 - Md5Str 校验失败.
	 * Error4 - 课件信息异常.
	 * Error5 - 无效用户名.
	 * Error6 - 课程未授权.
	 */
	String loadVideo(String userName,Integer type,String videoId) throws Exception;
}