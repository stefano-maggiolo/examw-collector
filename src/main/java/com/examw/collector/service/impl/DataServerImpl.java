package com.examw.collector.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.examw.collector.service.IDataServer;
import com.examw.collector.service.IRemoteDataProxy;
import com.examw.utils.XmlUtil;

/**
 * 数据服务实现。
 * @author yangyong.
 * @since 2014-06-26.
 */
public class DataServerImpl  implements IDataServer {
	private static Logger logger = Logger.getLogger(DataServerImpl.class);
	private IRemoteDataProxy remoteDataProxy;
	/**
	 * 设置远程数据服务接口。
	 * @param remoteDataProxy
	 */
	public void setRemoteDataProxy(IRemoteDataProxy remoteDataProxy) {
		this.remoteDataProxy = remoteDataProxy;
	}
	/*
	 * 加载课程类别集合。
	 * @see com.examw.collector.service.IDataServer#loadCatalogs()
	 */
	@Override
	public List<Catalog> loadCatalogs() {
		try {
			logger.info("加载课程类别集合...");
			String xml = this.remoteDataProxy.loadLesson(1, null, null, null);
			if(StringUtils.isEmpty(xml)) return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, ".//up_type");
			if(list == null || list.getLength() == 0) return null;
			List<Catalog> catalogs = new ArrayList<>();
			for(int i = 0; i < list.getLength(); i++){
				Node node = list.item(i);
				if(node == null) continue;
				Catalog catalog = new Catalog();
				catalog.setCode(XmlUtil.getNodeStringValue(node, "./up_type_code"));
				catalog.setName(XmlUtil.getNodeStringValue(node, "./up_type_name"));
				catalog.setClassTotal(Integer.parseInt(XmlUtil.getNodeStringValue(node, "./class_num")));
				
				NodeList children = XmlUtil.selectNodes(node, "./lesson_type");
				if(children != null && children.getLength() > 0){
					Set<Catalog> sets = new HashSet<>();
					for(int j = 0; j < children.getLength(); j++){
						Catalog c = this.buildChildCatalog(children.item(j));
						if(c != null){
							c.setParent(catalog);
							sets.add(c);
						}
					}
					if(sets.size() > 0) {
						catalog.setChildren(sets);
					}
				}
				catalogs.add(catalog);
			}
			return catalogs;
		} catch (SAXException | ParserConfigurationException | XPathExpressionException | IOException e) {
			logger.error(e);
			e.printStackTrace();
		} 
		return null;
	}
	/**
	 * 构建子课程类型。
	 * @param node
	 * @return
	 * @throws XPathExpressionException 
	 */
	private Catalog buildChildCatalog(Node node) throws XPathExpressionException{
		if(node == null) return null;
		Catalog catalog = new Catalog();
		catalog.setCode(XmlUtil.getNodeStringValue(node, "./lesson_type_code"));
		catalog.setName(XmlUtil.getNodeStringValue(node, "./lesson_type_name"));
		catalog.setClassTotal(Integer.parseInt(XmlUtil.getNodeStringValue(node, "./class_num")));
		NodeList lessonList = XmlUtil.selectNodes(node, ".//lesson");
		if(lessonList != null && lessonList.getLength() > 0){
			Set<Subject> subjects = new HashSet<>();
			for(int i = 0; i < lessonList.getLength(); i++){
				Subject subject = this.buildSubject(lessonList.item(i));
				if(subject != null){
					subject.setCatalog(catalog);
					subjects.add(subject);
				}
			}
			if(subjects.size() > 0){
				catalog.setSubjects(subjects);
			}
		}
		return catalog;
	}
	/**
	 * 构建课程科目。
	 * @param node
	 * @return
	 * @throws XPathExpressionException 
	 */
	private Subject buildSubject(Node node) throws XPathExpressionException{
		if(node == null) return null;
		Subject subject = new Subject();
		subject.setCode(XmlUtil.getNodeStringValue(node, "./lesson_code"));
		subject.setName(XmlUtil.getNodeStringValue(node, "./lesson_name"));
		subject.setClassTotal(Integer.parseInt(XmlUtil.getNodeStringValue(node, "./class_num")));
		return subject;
	}
	/*
	 * 单个科目中班别的信息集合。
	 * @see com.examw.collector.service.IDataServer#loadClasses(java.lang.String, java.lang.String)
	 */
	@Override
	public List<SubClass> loadClasses(String lesson_type_code, String lesson_code) {
		try{
			logger.info("单个科目中班级的信息集合...");
			if(StringUtils.isEmpty(lesson_type_code) || StringUtils.isEmpty(lesson_code)) return null;
			String xml = this.remoteDataProxy.loadLesson(2, lesson_type_code, lesson_code, null);
			if(StringUtils.isEmpty(xml)) return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, ".//lesson[lesson_code='"+ lesson_code+"']//class");
			if(list == null || list.getLength() == 0) return null;
			List<SubClass> subClasses = new ArrayList<>();
			for(int i = 0; i < list.getLength(); i++){
				if(list.item(i) == null) continue;
				SubClass data = new SubClass();
				data.setCode(XmlUtil.getNodeStringValue(list.item(i), "./class_code"));
				data.setName(XmlUtil.getNodeStringValue(list.item(i), "./class_name"));
				data.setIsLive(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_live")));
				data.setLongDay(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./long_day")));
				data.setStart(XmlUtil.getNodeStringValue(list.item(i), "./start_date"));
				data.setEnd(XmlUtil.getNodeStringValue(list.item(i), "./end_date"));
				data.setTeacherName(XmlUtil.getNodeStringValue(list.item(i), "./teacher_name"));
				data.setSourcePrice(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./source_price")));
				data.setSalePrice(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./sale_price")));
				data.setTotal(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./relate_sum")));
				data.setDemo(XmlUtil.getNodeStringValue(list.item(i), "./relate_demo"));
				data.setIsShow(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_show")));
				Node node = XmlUtil.selectSingleNode(list.item(i), "./ad_video");
				if(node != null){
					AdVideo av = new AdVideo();
					av.setType(Integer.parseInt(XmlUtil.getNodeStringValue(node, "./video_type")));
					av.setCode(XmlUtil.getNodeStringValue(node, "./video_code"));
					av.setName(XmlUtil.getNodeStringValue(node, "./video_name"));
					av.setAddress(XmlUtil.getNodeStringValue(node, "./video_address"));
					data.setAdVideo(av);
				}
				subClasses.add(data);
			}
			return subClasses;
		}catch(IOException | XPathExpressionException | SAXException | ParserConfigurationException e){
			logger.error(e);
			e.printStackTrace(); 
		}
		return null;
	}
	/*
	 * 获取课时（讲）集合。
	 * @see com.examw.collector.service.IDataServer#loadRelates(java.lang.String)
	 */
	@Override
	public List<Relate> loadRelates(String class_code) {
		try{
			logger.info("获取课时（讲）集合...");
			if(StringUtils.isEmpty(class_code)) return null;
			String xml = this.remoteDataProxy.loadLesson(3, null, null, class_code);
			if(StringUtils.isEmpty(xml)) return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, "//relate");
			if(list == null || list.getLength() == 0) return null;
			List<Relate> relates = new ArrayList<>();
			for(int i = 0; i < list.getLength(); i++){
				if(list.item(i) == null) continue;
				Relate data = new Relate();
				data.setNum(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./relate_num")));
				data.setName(XmlUtil.getNodeStringValue(list.item(i), "./relate_name"));
				data.setDemo((XmlUtil.getNodeStringValue(list.item(i), "./relate_demo")).equalsIgnoreCase("1"));
				data.setUpdate(XmlUtil.getNodeStringValue(list.item(i), "./update_date"));
				data.setNew((XmlUtil.getNodeStringValue(list.item(i), "./relate_demo")).equalsIgnoreCase("1"));
				data.setAddress(XmlUtil.getNodeStringValue(list.item(i), "./listen_address"));
				relates.add(data);
			}
			return relates;
		}catch(IOException | SAXException | ParserConfigurationException | XPathExpressionException e){
			logger.error(e);
			e.printStackTrace(); 
		}  
		return null;
	}
	/*
	 * 获取单个科目中套餐的信息接口。
	 * @see com.examw.collector.service.IDataServer#loadPacks(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Pack> loadPacks(String lesson_type_code, String lesson_code) {
		try{
			logger.info("获取单个科目中套餐的信息接口...");
			if(StringUtils.isEmpty(lesson_type_code) || StringUtils.isEmpty(lesson_code)) return null;
			String xml = this.remoteDataProxy.loadLesson(4, lesson_type_code, lesson_code, null);
			if(StringUtils.isEmpty(xml)) return null;
			Document document = XmlUtil.loadDocument(xml);
			Element root = document.getDocumentElement();
			NodeList list = XmlUtil.selectNodes(root, ".//lesson[lesson_code='"+ lesson_code+"']//discount");
			if(list == null || list.getLength() == 0) return null;
			List<Pack> packs = new ArrayList<>();
			for(int i = 0; i < list.getLength(); i++){
				if(list.item(i) == null) continue;
				Pack data = new Pack();
				data.setCode(XmlUtil.getNodeStringValue(list.item(i), "./discount_code"));
				data.setName(XmlUtil.getNodeStringValue(list.item(i), "./discount_name"));
				data.setSource(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./source_amount")));
				data.setDiscount(Integer.parseInt(XmlUtil.getNodeStringValue(list.item(i), "./discount_amount")));
				data.setShow(Boolean.parseBoolean(XmlUtil.getNodeStringValue(list.item(i), "./is_show")));
				data.setClassCodes(XmlUtil.getNodeStringValue(list.item(i), "./class_code").split(","));
				packs.add(data);
			}
			return packs;
		}catch(IOException | SAXException | ParserConfigurationException | XPathExpressionException e){
			logger.error(e);
			e.printStackTrace(); 
		}
		return null;
	}
	
}