package com.examw.collector.test;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examw.collector.domain.Catalog;
import com.examw.collector.domain.SubClass;
import com.examw.collector.service.IDataServer;
import com.thoughtworks.xstream.XStream;

/**
 * 远程课程数据测试。
 * @author yangyong.
 * @since 2014-06-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-collector.xml"})
public class RemoteLessonTest {
	@Resource
	private IDataServer dataServer;
	/**
	 * 加载课程数据。
	 * @throws IOException 
	 */
	//@Test
	public void loadCataData() throws IOException{
		System.out.print("开始获取课程类别：");
		List<Catalog> list = this.dataServer.loadCatalogs();
		
		XStream xStream = new XStream();
		String xml = xStream.toXML(list);
		System.out.print(xml);
	}
	@Test
	public void loadClasses(){
		List<SubClass>  list = this.dataServer.loadClasses("700", "659");
		XStream xStream = new XStream();
		String xml = xStream.toXML(list);
		System.out.print(xml);
	}
}