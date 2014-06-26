package com.examw.collector.test;

import java.io.IOException;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examw.collector.service.IRemoteDataProxy;

/**
 * 远程课程数据测试。
 * @author yangyong.
 * @since 2014-06-26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-collector.xml"})
public class RemoteLessonTest {
	@Resource
	private IRemoteDataProxy remoteDataProxy;
	/**
	 * 加载课程数据。
	 * @throws IOException 
	 */
	@Test
	public void loadCataData() throws IOException{
		System.out.print("开始获取课程类别：");
		String data = this.remoteDataProxy.loadLesson(1, null, null, null);
		System.out.print(data);
	}
}