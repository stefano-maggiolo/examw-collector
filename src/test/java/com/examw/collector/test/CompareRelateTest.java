package com.examw.collector.test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.examw.collector.domain.Relate;

/**
 * 
 * @author fengwei.
 * @since 2014年7月25日 上午10:55:49.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
public class CompareRelateTest {
	@Test
	public void compareTest(){
		Relate r1 = new Relate();
		r1.setNum(11254);
		Relate r2 = new Relate();
		r2.setNum(11325);
		Relate r3 = new Relate();
		r3.setNum(11452);
		List<Relate> list = new ArrayList<>();
		list.add(r2);
		list.add(r1);
		list.add(r3);
		show(list);
		Collections.sort(list);
		show(list);
		
	}
	private void show(List<Relate> list){
		for(Relate r:list)
		{
			System.out.print(r.getNum()+" ");
		}
		System.out.println("==========");
	}
}
