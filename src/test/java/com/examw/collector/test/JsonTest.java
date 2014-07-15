package com.examw.collector.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.examw.collector.domain.User;
import com.examw.collector.support.JSONUtil;

/**
 * 
 * @author fengwei.
 * @since 2014年7月14日 上午11:45:44.
 */
public class JsonTest {
	@Test
	public void testJson(){
		List<User> list = new ArrayList<User>();
		User user = new User();
		user.setAccount("hahaha");
		user.setName("姓名");
		list.add(user);
		System.out.println(JSONUtil.ObjectToJson(user));
		System.out.println(JSONUtil.ObjectToJson(list));
		
		String userString = "{\"id\":null,\"name\":\"姓名\",\"account\":\"hahaha\",\"password\":null,\"phone\":null,\"qq\":null,\"email\":null,\"lastLoginIP\":null,\"type\":null,\"gender\":null,\"status\":null,\"createTime\":null,\"lastLoginTime\":null}";
		String listString = "[{\"id\":null,\"name\":\"姓名\",\"account\":\"hahaha\",\"password\":null,\"phone\":null,\"qq\":null,\"email\":null,\"lastLoginIP\":null,\"type\":null,\"gender\":null,\"status\":null,\"createTime\":null,\"lastLoginTime\":null}]";
		
		User u = JSONUtil.JsonToObject(userString, User.class);
		System.out.println(u.getAccount());
		@SuppressWarnings("unchecked")
		List<User> l = JSONUtil.JsonToCollection(listString, List.class,User.class);
		System.out.println(l.get(0).getAccount());
	}
}
