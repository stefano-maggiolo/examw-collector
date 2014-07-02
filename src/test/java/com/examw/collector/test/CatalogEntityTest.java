package com.examw.collector.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.examw.collector.model.local.CatalogEntityInfo;
import com.examw.collector.service.ICatalogEntityService;

/**
 * 
 * @author fengwei.
 * @since 2014年7月2日 上午10:42:19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-examw-collector.xml"})
public class CatalogEntityTest {
	@Resource
	private ICatalogEntityService catalogEntityService;
	@Test
	public void loadCatalogs(){
		List<CatalogEntityInfo>  list = this.catalogEntityService.datagrid(new CatalogEntityInfo()).getRows();
		System.out.println(list.size());
	}
}
