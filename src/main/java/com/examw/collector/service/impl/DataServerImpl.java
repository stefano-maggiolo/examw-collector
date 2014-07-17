package com.examw.collector.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.examw.collector.domain.AdVideo;
import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.Pack;
import com.examw.collector.domain.Relate;
import com.examw.collector.domain.SubClass;
import com.examw.collector.domain.Subject;
import com.examw.collector.domain.local.TeacherEntity;
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IRemoteDataProxy;
import com.examw.utils.XmlUtil;

/**
 * 数据服务实现。
 * 
 * @author yangyong.
 * @since 2014-06-26.
 */
public class DataServerImpl implements IDataServer {
	private static Logger logger = Logger.getLogger(DataServerImpl.class);
	private IRemoteDataProxy remoteDataProxy;
	
	private String savePath;
	
	/**
	 * 设置 图片文件保存位置
	 * @param savePath
	 * 
	 */
	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	/**
	 * 设置远程数据服务接口。
	 * 
	 * @param remoteDataProxy
	 */
	public void setRemoteDataProxy(IRemoteDataProxy remoteDataProxy) {
		this.remoteDataProxy = remoteDataProxy;
	}

	/*
	 * 加载课程类别集合。
	 * 
	 * @see com.examw.collector.service.IDataServer#loadCatalogs()
	 */
	@Override
	public List<Catalog> loadCatalogs() {
		try {
			logger.info("加载课程类别集合...");
			String xml = this.remoteDataProxy.loadLesson(1, null, null, null);
			if (StringUtils.isEmpty(xml))
				return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, ".//up_type");
			if (list == null || list.getLength() == 0)
				return null;
			List<Catalog> catalogs = new ArrayList<>();
			for (int i = 0; i < list.getLength(); i++) {
				Node node = list.item(i);
				if (node == null)
					continue;
				Catalog catalog = new Catalog();
				catalog.setCode(XmlUtil.getNodeStringValue(node,
						"./up_type_code"));
				catalog.setName(XmlUtil.getNodeStringValue(node,
						"./up_type_name"));
				catalog.setClassTotal(Integer.parseInt(XmlUtil
						.getNodeStringValue(node, "./class_num")));
				catalog.setParent(null);
				NodeList children = XmlUtil.selectNodes(node, "./lesson_type");
				if (children != null && children.getLength() > 0) {
					Set<Catalog> sets = new HashSet<>();
					for (int j = 0; j < children.getLength(); j++) {
						Catalog c = this.buildChildCatalog(children.item(j));
						if (c != null) {
							c.setParent(catalog);
							sets.add(c);
						}
					}
					if (sets.size() > 0) {
						catalog.setChildren(sets);
					}
				}
				catalogs.add(catalog);
			}
			return catalogs;
		} catch (SAXException | ParserConfigurationException
				| XPathExpressionException | IOException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构建子课程类型。
	 * 
	 * @param node
	 * @return
	 * @throws XPathExpressionException
	 */
	private Catalog buildChildCatalog(Node node)
			throws XPathExpressionException {
		if (node == null)
			return null;
		Catalog catalog = new Catalog();
		catalog.setCode(XmlUtil.getNodeStringValue(node, "./lesson_type_code"));
		catalog.setName(XmlUtil.getNodeStringValue(node, "./lesson_type_name"));
		catalog.setClassTotal(Integer.parseInt(XmlUtil.getNodeStringValue(node,
				"./class_num")));
		NodeList lessonList = XmlUtil.selectNodes(node, ".//lesson");
		if (lessonList != null && lessonList.getLength() > 0) {
			Set<Subject> subjects = new HashSet<>();
			for (int i = 0; i < lessonList.getLength(); i++) {
				Subject subject = this.buildSubject(lessonList.item(i));
				if (subject != null) {
					subject.setCatalog(catalog);
					subjects.add(subject);
				}
			}
			if (subjects.size() > 0) {
				catalog.setSubjects(subjects);
			}
		}
		return catalog;
	}

	/**
	 * 构建课程科目。
	 * 
	 * @param node
	 * @return
	 * @throws XPathExpressionException
	 */
	private Subject buildSubject(Node node) throws XPathExpressionException {
		if (node == null)
			return null;
		Subject subject = new Subject();
		subject.setCode(XmlUtil.getNodeStringValue(node, "./lesson_code"));
		subject.setName(XmlUtil.getNodeStringValue(node, "./lesson_name"));
		subject.setClassTotal(Integer.parseInt(XmlUtil.getNodeStringValue(node,
				"./class_num")));
		return subject;
	}

	/*
	 * 单个科目中班别的信息集合。
	 * 
	 * @see
	 * com.examw.collector.service.IDataServer#loadClasses(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<SubClass> loadClasses(String lesson_type_code,
			String lesson_code) {
		try {
			logger.info("单个科目中班级的信息集合...");
			if (StringUtils.isEmpty(lesson_type_code))
				return null;
			if(StringUtils.isEmpty(lesson_code))
				return loadClasses(lesson_type_code);
			String xml = this.remoteDataProxy.loadLesson(2, lesson_type_code,
					lesson_code, null);
			if (StringUtils.isEmpty(xml))
				return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, ".//lesson[lesson_code='"	+ lesson_code + "']//class");
			if (list == null || list.getLength() == 0)
				return null;
			List<SubClass> subClasses = new ArrayList<>();
			// =========增加科目===================
			Catalog catalog = new Catalog();
			catalog.setCode(lesson_type_code);
			// ====================================
			// =========增加科目===================
			Subject subject = null;
			if (!StringUtils.isEmpty(lesson_code)) {
				subject = new Subject();
				subject.setCode(lesson_code);
			}
			// ====================================
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) == null)
					continue;
				SubClass data = new SubClass();
				// =========增加科目=============
				data.setCatalog(catalog);
				data.setSubject(subject);
				// ==============================
				data.setCode(XmlUtil.getNodeStringValue(list.item(i),"./class_code"));
				data.setName(XmlUtil.getNodeStringValue(list.item(i),"./class_name"));
				data.setIsLive(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_live")));
				data.setLongDay(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./long_day")));
				data.setStart(XmlUtil.getNodeStringValue(list.item(i),"./start_date"));
				data.setEnd(XmlUtil.getNodeStringValue(list.item(i),"./end_date"));
				data.setTeacherId(XmlUtil.getNodeStringValue(list.item(i),"./teacher_id"));
				data.setTeacherName(XmlUtil.getNodeStringValue(list.item(i),"./teacher_name"));
				data.setSourcePrice(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./source_price")));
				data.setSalePrice(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./sale_price")));
				data.setTotal(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./relate_sum")));
				data.setDemo(XmlUtil.getNodeStringValue(list.item(i),"./relate_demo"));
				data.setIsShow(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_show")));
				Node node = XmlUtil.selectSingleNode(list.item(i), "./ad_video");
				if (node != null) {
					AdVideo av = new AdVideo();
					av.setType(Integer.parseInt(XmlUtil.getNodeStringValue(
							node, "./video_type")));
					av.setCode(XmlUtil.getNodeStringValue(node, "./video_code"));
					av.setName(XmlUtil.getNodeStringValue(node, "./video_name"));
					av.setAddress(XmlUtil.getNodeStringValue(node,
							"./video_address"));
					data.setAdVideo(av);
				}
				subClasses.add(data);
			}
			return subClasses;
		} catch (IOException | XPathExpressionException | SAXException
				| ParserConfigurationException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	// 获取一个类型下所有的班级
	public List<SubClass> loadClasses(String lesson_type_code) {
		try {
			logger.info("整个类别下的班级的信息集合...");
			if (StringUtils.isEmpty(lesson_type_code))
				return null;
			String xml = this.remoteDataProxy.loadLesson(2, lesson_type_code,
					null, null);
			if (StringUtils.isEmpty(xml))
				return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList lessonList = XmlUtil.selectNodes(root, ".//lesson");
			if (lessonList == null || lessonList.getLength() == 0)
				return null;
			List<SubClass> subClasses = new ArrayList<>();
			// =========增加科目===================
			Catalog catalog = new Catalog();
			catalog.setCode(lesson_type_code);
			// ====================================
			for (int j = 0; j < lessonList.getLength(); j++) {
				if (lessonList.item(j) == null)
					continue;
				Subject subject = new Subject();
				subject.setCode(XmlUtil.getNodeStringValue(lessonList.item(j),"./lesson_code"));
				subject.setName(XmlUtil.getNodeStringValue(lessonList.item(j),"./lesson_name"));
				NodeList list = XmlUtil.selectNodes(lessonList.item(j),".//class");
				for (int i = 0; i < list.getLength(); i++) {
					if (list.item(i) == null)
						continue;
					SubClass data = new SubClass();
					// =========增加科目=============
					data.setCatalog(catalog);
					data.setSubject(subject);
					// ==============================
					data.setCode(XmlUtil.getNodeStringValue(list.item(i),"./class_code"));
					data.setName(XmlUtil.getNodeStringValue(list.item(i),"./class_name"));
					data.setIsLive(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_live")));
					data.setLongDay(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./long_day")));
					data.setStart(XmlUtil.getNodeStringValue(list.item(i),"./start_date"));
					data.setEnd(XmlUtil.getNodeStringValue(list.item(i),"./end_date"));
					data.setTeacherId(XmlUtil.getNodeStringValue(list.item(i), "./teacher_id"));//teacher_id
					data.setTeacherName(XmlUtil.getNodeStringValue(list.item(i), "./teacher_name"));
					data.setSourcePrice(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./source_price")));
					data.setSalePrice(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./sale_price")));
					data.setTotal(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./relate_sum")));
					data.setDemo(XmlUtil.getNodeStringValue(list.item(i),"./relate_demo"));
					data.setIsShow(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_show")));
					Node node = XmlUtil.selectSingleNode(list.item(i),"./ad_video");
					if (node != null) {
						AdVideo av = new AdVideo();
						av.setType(Integer.parseInt(XmlUtil.getNodeStringValue(node, "./video_type")));
						av.setCode(XmlUtil.getNodeStringValue(node,"./video_code"));
						av.setName(XmlUtil.getNodeStringValue(node,"./video_name"));
						av.setAddress(XmlUtil.getNodeStringValue(node,"./video_address"));
						data.setAdVideo(av);
					}
					subClasses.add(data);
				}
			}
			return subClasses;
		} catch (IOException | XPathExpressionException | SAXException
				| ParserConfigurationException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取课时（讲）集合。
	 * 
	 * @see
	 * com.examw.collector.service.IDataServer#loadRelates(java.lang.String)
	 */
	@Override
	public List<Relate> loadRelates(String class_code) {
		try {
			logger.info("获取课时（讲）集合...");
			if (StringUtils.isEmpty(class_code))
				return null;
			String xml = this.remoteDataProxy.loadLesson(3, null, null,
					class_code);
			if (StringUtils.isEmpty(xml))
				return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, "//relate");
			if (list == null || list.getLength() == 0)
				return null;
			List<Relate> relates = new ArrayList<>();
			// ==================================================
			SubClass grade = new SubClass();
			grade.setCode(class_code);
			// ==================================================
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) == null)
					continue;
				Relate data = new Relate();
				// ==================================================
				data.setSubclass(grade);
				// ==================================================
				data.setNum(Integer.parseInt(XmlUtil.getNodeStringValue(
						list.item(i), "./relate_num")));
				data.setName(XmlUtil.getNodeStringValue(list.item(i),
						"./relate_name"));
				data.setIsDemo((XmlUtil.getNodeStringValue(list.item(i),
						"./relate_demo")).equalsIgnoreCase("1"));
				data.setUpdateDate(XmlUtil.getNodeStringValue(list.item(i),
						"./update_date"));
				data.setIsNew((XmlUtil.getNodeStringValue(list.item(i),
						"./relate_demo")).equalsIgnoreCase("1"));
				data.setAddress(XmlUtil.getNodeStringValue(list.item(i),
						"./listen_address"));
				relates.add(data);
			}
			return relates;
		} catch (IOException | SAXException | ParserConfigurationException
				| XPathExpressionException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * 获取单个科目中套餐的信息接口。
	 * 
	 * @see com.examw.collector.service.IDataServer#loadPacks(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public List<Pack> loadPacks(String lesson_type_code, String lesson_code) {
		try {
			logger.info("获取单个科目中套餐的信息接口...	lesson_code非必需[修改了下]");
			if (StringUtils.isEmpty(lesson_type_code))
				return null;
			if (StringUtils.isEmpty(lesson_code)) // 取全科套餐
				return loadPacks(lesson_type_code);
			String xml = this.remoteDataProxy.loadLesson(4, lesson_type_code,
					lesson_code, null);
			if (StringUtils.isEmpty(xml))
				return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, ".//lesson[lesson_code='"
						+ lesson_code + "']//discount");
			if (list == null || list.getLength() == 0)
				return null;
			List<Pack> packs = new ArrayList<>();
			// ==================================================
			Catalog catalog = new Catalog();
			catalog.setCode(lesson_type_code);
			// ==================================================
			// ==================================================
			Subject subject = null;
			if (!StringUtils.isEmpty(lesson_code)) {
				subject = new Subject();
				subject.setCode(lesson_code);
			}
			// ==================================================
			for (int i = 0; i < list.getLength(); i++) {
				if (list.item(i) == null)
					continue;
				Pack data = new Pack();
				// ==================================================
				data.setCatalog(catalog);
				data.setSubject(subject);
				// ==================================================
				data.setCode(XmlUtil.getNodeStringValue(list.item(i),
						"./discount_code"));
				data.setName(XmlUtil.getNodeStringValue(list.item(i),
						"./discount_name"));
				data.setSource(Integer.parseInt(XmlUtil.getNodeStringValue(
						list.item(i), "./source_amount")));
				data.setDiscount(Integer.parseInt(XmlUtil.getNodeStringValue(
						list.item(i), "./discount_amount")));
				data.setIsShow(Boolean.parseBoolean(XmlUtil.getNodeStringValue(
						list.item(i), "./is_show")));
				// data.setClassCodes(XmlUtil.getNodeStringValue(list.item(i),
				// "./class_code").split(","));
				data.setClassCodes(XmlUtil.getNodeStringValue(list.item(i),
						"./class_code"));
				packs.add(data);
			}
			return packs;
		} catch (IOException | SAXException | ParserConfigurationException
				| XPathExpressionException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}
	//获取整个类别下套餐的信息接口...
	public List<Pack> loadPacks(String lesson_type_code){
		try{
			logger.info("获取整个类别下套餐的信息接口...");
			String xml = this.remoteDataProxy.loadLesson(4, lesson_type_code,null, null);
			if (StringUtils.isEmpty(xml))
				return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			//获取整个大类下的所有套餐
			NodeList lessonList = XmlUtil.selectNodes(root, ".//lesson");
			if (lessonList == null || lessonList.getLength() == 0)
				return null;
			List<Pack> packs = new ArrayList<>();
			// ==================================================
			Catalog catalog = new Catalog();
			catalog.setCode(lesson_type_code);
			// ==================================================
			for (int j = 0; j < lessonList.getLength(); j++) {
				if (lessonList.item(j) == null)
					continue;
				Subject subject = new Subject();
				subject.setCode(XmlUtil.getNodeStringValue(lessonList.item(j),"./lesson_code"));
				subject.setName(XmlUtil.getNodeStringValue(lessonList.item(j),"./lesson_name"));
				//如果lesson_code == 子类别的ID表示全科套餐
				if(lesson_type_code.equals(subject.getCode())){
					subject = null;
				}
				//其下的套餐
				NodeList list = XmlUtil.selectNodes(lessonList.item(j),".//discount");
				for (int i = 0; i < list.getLength(); i++) {
					if (list.item(i) == null)
						continue;
					Pack data = new Pack();
					// ==================================================
					data.setCatalog(catalog);
					data.setSubject(subject);
					// ==================================================
					data.setCode(XmlUtil.getNodeStringValue(list.item(i),
							"./discount_code"));
					data.setName(XmlUtil.getNodeStringValue(list.item(i),
							"./discount_name"));
					data.setSource(Integer.parseInt(XmlUtil.getNodeStringValue(
							list.item(i), "./source_amount")));
					data.setDiscount(Integer.parseInt(XmlUtil.getNodeStringValue(
							list.item(i), "./discount_amount")));
					data.setIsShow(Boolean.parseBoolean(XmlUtil.getNodeStringValue(
							list.item(i), "./is_show")));
					// data.setClassCodes(XmlUtil.getNodeStringValue(list.item(i),
					// "./class_code").split(","));
					data.setClassCodes(XmlUtil.getNodeStringValue(list.item(i),
							"./class_code"));
					packs.add(data);
				}
			}
			return packs;
		}catch (IOException | SAXException | ParserConfigurationException
				| XPathExpressionException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return null;
	}
	
	
	/*
	 * 注册。
	 * 
	 * @see com.examw.collector.service.IDataServer#register(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean register(String userName, String password, String name,
			String email, String province, String tel) throws Exception {
		logger.info("注册...");
		if (StringUtils.isEmpty(userName))
			throw new Exception("注册的用户名不能为空！");
		if (StringUtils.isEmpty(password))
			throw new Exception("注册的密码不能为空！");
		if (StringUtils.isEmpty(name))
			throw new Exception("姓名不能为空！");
		String xml = this.remoteDataProxy.postUsers(1, null, null, null, null,
				null, null, null, userName, password, name, email, province,
				tel);
		if (StringUtils.isEmpty(xml))
			throw new Exception("未获取服务端反馈！");
		Integer result = this.checkCallbackCode(xml);
		String err = null;
		if (result == -1) {
			logger.info(err = "反馈数据校验失败！");
			throw new Exception(err);
		}
		if (result == 2) {
			logger.info(err = "用户已存在！");
			throw new Exception(err);
		}
		if (result == 3) {
			logger.info(err = "用户名、密码字符串不合法！");
			throw new Exception(err);
		}
		if (result == 4) {
			logger.info(err = "失败！");
			return false;
		}
		return result == 1;
	}

	/*
	 * 用户登录。
	 * 
	 * @see com.examw.collector.service.IDataServer#login(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public boolean login(String userName, String password) throws Exception {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
			throw new Exception("用户名或密码不能为空！");
		String xml = this.remoteDataProxy.postUsers(2, null, null, null, null,
				null, null, null, userName, password, null, null, null, null);
		if (StringUtils.isEmpty(xml))
			throw new Exception("未获取服务端反馈！");
		Integer result = this.checkCallbackCode(xml);
		String err = null;
		if (result == 2) {
			logger.info(err = "用户名、密码错误！");
			throw new Exception(err);
		}
		if (result == 3) {
			logger.info(err = "用户已禁用！");
			throw new Exception(err);
		}
		if (result == 4) {
			logger.info(err = "失败！");
			return false;
		}
		return result == 1;
	}

	/*
	 * 用户选课。
	 * 
	 * @see
	 * com.examw.collector.service.IDataServer#choiceCourse(java.lang.String,
	 * java.lang.String, java.lang.Integer, java.lang.String[])
	 */
	@Override
	public boolean choiceCourse(String userName, String password, Integer type,
			String[] codes) throws Exception {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
			throw new Exception("用户名或密码不能为空！");
		String[] lesson_code = (type == 1) ? codes : null, discount_code = (type == 2) ? codes
				: null;
		String xml = this.remoteDataProxy.postUsers(3, null, lesson_code,
				discount_code, null, null, null, null, userName, password,
				null, null, null, null);
		if (StringUtils.isEmpty(xml))
			throw new Exception("未获取服务端反馈！");
		Integer result = this.checkCallbackCode(xml);
		String err = null;
		if (result == 2) {
			logger.info(err = "用户名、密码错误！");
			throw new Exception(err);
		}
		if (result == 3) {
			logger.info(err = "用户已禁用！");
			throw new Exception(err);
		}
		if (result == 4) {
			logger.info(err = "课程已过期！");
			throw new Exception(err);
		}
		if (result == 5) {
			logger.info(err = "套餐已过期！");
			throw new Exception(err);
		}
		if (result == 6) {
			logger.info(err = "课程或套餐参数不正确！");
			throw new Exception(err);
		}
		if (result == 7) {
			logger.info(err = "失败！");
			return false;
		}
		if (result == 9) {
			logger.info(err = "重复选课！");
			throw new Exception(err);
		}
		if (result == 99) {
			logger.info(err = "不能重复提交订单请求！");
			throw new Exception(err);
		}
		return result == 1;
	}

	/*
	 * 充值。
	 * 
	 * @see com.examw.collector.service.IDataServer#recharge(java.lang.String,
	 * java.lang.String, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean recharge(String userName, String password, Integer money,
			String cardnum, String cardpwd) throws Exception {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
			throw new Exception("用户名或密码不能为空！");
		if (StringUtils.isEmpty(cardnum) || StringUtils.isEmpty(cardpwd))
			throw new Exception("学习卡或密码为不能为空！");
		String xml = this.remoteDataProxy.postUsers(4, null, null, null, null,
				money.toString(), cardnum, cardpwd, userName, password, null,
				null, null, null);
		if (StringUtils.isEmpty(xml))
			throw new Exception("未获取服务端反馈！");
		Integer result = this.checkCallbackCode(xml);
		String err = null;
		if (result == 2) {
			logger.info(err = "用户名、密码错误！");
			throw new Exception(err);
		}
		if (result == 3) {
			logger.info(err = "学习卡未激活或已冻结！");
			throw new Exception(err);
		}
		if (result == 4) {
			logger.info(err = "学习卡已过期！");
			throw new Exception(err);
		}
		if (result == 5) {
			logger.info(err = "学习卡余额不足！");
			throw new Exception(err);
		}
		if (result == 6) {
			logger.info(err = "用户名、密码错误！");
			throw new Exception(err);
		}
		if (result == 7) {
			logger.info(err = "学习卡账号或密码校验失败！");
			throw new Exception(err);
		}
		if (result == 8) {
			logger.info(err = "失败！");
			return false;
		}
		if (result == 9) {
			logger.info(err = "用户已经使用过优惠卡充值！");
			throw new Exception(err);
		}
		return result == 1;
	}

	/*
	 * 扣费。
	 * 
	 * @see com.examw.collector.service.IDataServer#payment(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.Integer)
	 */
	@Override
	public boolean payment(String userName, String password, String orderCode,
			Integer money) throws Exception {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
			throw new Exception("用户名或密码不能为空！");
		String xml = this.remoteDataProxy.postUsers(5, orderCode, null, null,
				null, money.toString(), null, null, userName, password, null,
				null, null, null);
		if (StringUtils.isEmpty(xml))
			throw new Exception("未获取服务端反馈！");
		Integer result = this.checkCallbackCode(xml);
		String err = null;
		if (result == 2) {
			logger.info(err = "定单号不存在！");
			throw new Exception(err);
		}
		if (result == 3) {
			logger.info(err = "用户余额不足！");
			throw new Exception(err);
		}
		if (result == 4) {
			logger.info(err = "扣款的金额不正确！");
			throw new Exception(err);
		}
		if (result == 5) {
			logger.info(err = "此订单已支付！");
			throw new Exception(err);
		}
		if (result == 6) {
			logger.info(err = "用户名、密码错误！");
			throw new Exception(err);
		}
		if (result == 7) {
			logger.info(err = "订单状态转化失败！");
			throw new Exception(err);
		}
		if (result == 8) {
			logger.info("失败！");
			return false;
		}
		return result == 1;
	}

	/*
	 * 学习。
	 * 
	 * @see com.examw.collector.service.IDataServer#study(java.lang.String,
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean study(String userName, String password, String lesson_code,
			String part) throws Exception {
		if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password))
			throw new Exception("用户名或密码不能为空！");
		String xml = this.remoteDataProxy.postUsers(6, null,
				new String[] { lesson_code }, null, part, null, null, null,
				null, password, null, null, null, null);
		if (StringUtils.isEmpty(xml))
			throw new Exception("未获取服务端反馈！");
		Integer result = this.checkCallbackCode(xml);
		String err = null;
		if (result == 2) {
			logger.info(err = "用户名、密码错误！");
			throw new Exception(err);
		}
		if (result == 3) {
			logger.info(err = "课程状态异常！");
			throw new Exception(err);
		}
		if (result == 4) {
			logger.info(err = "课程参数信息有误！");
			throw new Exception(err);
		}
		if (result == 5) {
			logger.info(err = "失败！");
			return false;
		}
		return result == 1;
	}

	/**
	 * 校验反馈数据。
	 * 
	 * @param xml
	 * @return 状态参数。
	 */
	private Integer checkCallbackCode(String xml) {
		try {
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			String state = XmlUtil.getNodeStringValue(root, "./state");
			String checkCode = this.remoteDataProxy
					.buildCheckCode(new String[] {
							XmlUtil.getNodeStringValue(root, "./cataid"),
							XmlUtil.getNodeStringValue(root, "./ordercode"),
							XmlUtil.getNodeStringValue(root, "./money"),
							XmlUtil.getNodeStringValue(root, "./username"),
							XmlUtil.getNodeStringValue(root, "./password"),
							XmlUtil.getNodeStringValue(root, "./time"),
							XmlUtil.getNodeStringValue(root, "./playurl"),
							state });
			if (checkCode.equalsIgnoreCase(XmlUtil.getNodeStringValue(root,
					"./md5Str"))) {
				return Integer.parseInt(state);
			}
		} catch (SAXException | IOException | ParserConfigurationException
				| XPathExpressionException e) {
			logger.error(e);
			e.printStackTrace();
		}
		return -1;
	}

	/*
	 * 定制大屏播放器.
	 * 
	 * @see com.examw.collector.service.IDataServer#loadVideo(java.lang.String,
	 * java.lang.Integer, java.lang.String)
	 */
	@Override
	public String loadVideo(String userName, Integer type, String videoId)
			throws Exception {
		if (StringUtils.isEmpty(userName))
			throw new Exception("用户名不能为空！");
		if (type == null)
			type = 3;
		return this.remoteDataProxy.loadVideo(videoId, type, userName);
	}
	
	@Override
	public TeacherEntity loadTeacher(String id) {
		if(StringUtils.isEmpty(id) ||id.equals("0")) return null;
		TeacherEntity teacher = null;
		try
		{
			teacher = new TeacherEntity();
			teacher.setId(id);
			String html = this.remoteDataProxy.loadTeacher(id);
			html = html.replaceAll("\\s", "");
			Pattern teacherName = Pattern.compile("<h2><dl><dt>(.+)</dt><ddclass=\"ms_itdt_dd1\"(.+)");
			Pattern teacherLessons = Pattern.compile("<dt><strong>(.+)</strong></dt><ddstyle=(.*)><fonttitle=(.*)>(.*)</font></dd></dl>");//</dt><ddstyle=(.+)<fonttile=(.+)>(.+)</font></dd></dl>");
			Pattern teacherInfo = Pattern.compile("<ul><li><pclass=\"dd_p_1center\">(.+)</p></li></ul>");
			Pattern teacherImg = Pattern.compile("<imgsrc=\"([/\\w\\d.]+)\"width=\"479px");
			Matcher m2 = teacherName.matcher(html);//group(1);
			while(m2.find()){
				String s = m2.group(1);
				if(s!=null){
					teacher.setName(s.replace("老师", ""));
					break;
				}
			}
		    m2 = teacherLessons.matcher(html); //group(4);
		    while(m2.find()){
				String s = m2.group(4);
				if(s!=null){
					teacher.setLessons(s);
					break;
				}
			}
		    m2 = teacherInfo.matcher(html);	//group(1);
		    while(m2.find()){
				String s = m2.group(1);
				if(s!=null){
					teacher.setDescription(s);
					teacher.setInfo(s);
					break;
				}
			}
		    m2 = teacherImg.matcher(html);	//group(1);
		    while(m2.find()){
				String s = m2.group(1);
				if(s!=null){
					if(StringUtils.isEmpty(savePath)){
						teacher.setImgurl("http://www.edu24ol.com"+s);
					}else{
						teacher.setImgurl(getImagePath("http://www.edu24ol.com"+s));
					}
					break;
				}
			}
		    teacher.setAddDate(new Date());
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		return teacher;
	}
	private String getImagePath(String url){
		String path = url;
		String fileName = url.substring(url.lastIndexOf("/")+1);
		//如果使用默认图片,地址有点不同
		if(fileName.equalsIgnoreCase("tc_w_b.gif"))
		{
			path = url = "http://www.edu24ol.com/web_teacher/images2011/tc_w_b.gif";
		}
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		String filePath = null;
		if(!savePath.endsWith("/")){
			filePath = savePath + File.separator + date;
		}else{
			filePath = savePath + date;
		}
		if(!new File(filePath).exists()){
			new File(filePath).mkdirs();
		}
		try{  
			java.net.HttpURLConnection conn =(java.net.HttpURLConnection) new URL(url).openConnection();  
            if (conn.getResponseCode() == 200) {  
              
                java.io.InputStream is = (java.io.InputStream) conn.getInputStream();  
                try{  
                    String realPath = filePath+File.separator+fileName;  
                    FileOutputStream baos = new FileOutputStream(new File(realPath));  
                    int buffer = 1024;  
                    byte[] b = new byte[buffer];  
                    int n = 0;  
                    while ((n = is.read(b, 0, buffer)) > 0) {  
                        baos.write(b, 0, n);  
                    }  
                    is.close();  
                    baos.close();  
                }catch(Exception e){  
                	e.printStackTrace();
                }  
                path = "/"+date+"/"+fileName;
            }else{  
            }  
        }catch(Exception e){  
                e.printStackTrace();  
        }  
		return path;
	}
}