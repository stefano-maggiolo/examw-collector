package com.examw.collector.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.examw.collector.dao.ICatalogEntityDao;
import com.examw.collector.domain.local.CatalogEntity;

/**
 * 
 * @author fengwei.
 * @since 2014年7月2日 上午10:42:19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-collector.xml"})
public class CatalogEntityTest {
	@Resource
	private ICatalogEntityDao catalogEntityDao;
	@Test
	@Transactional
	public void loadCatalogs(){
		CatalogEntity entity = this.catalogEntityDao.find("955");
		System.out.println(entity.getId());
	}
}
