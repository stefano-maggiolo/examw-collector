package com.examw.collector.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examw.collector.model.RelateInfo;
import com.examw.collector.service.IRelateService;

/**
 * 测试一下事务,好像没起作用
 * @author fengwei.
 * @since 2014年7月10日 下午3:05:23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-collector.xml"})
public class TransactionTest {
	@Resource 
	private IRelateService relateService;
	@Test
	public void testInsert(){
		RelateInfo info = new RelateInfo();
		info.setClassId("1147");
		this.relateService.init(info);
	}
}
