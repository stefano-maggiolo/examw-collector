package com.examw.collector.support;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;

/**
 * 
 * @author fengwei.
 * @since 2014年7月14日 上午11:06:30.
 */
public class JSONUtil {
	private static ObjectMapper mapper;

	public static synchronized ObjectMapper getMapperInstance() {
		if (mapper == null) {
			mapper = new ObjectMapper();
		}
		return mapper;
	}
	
	public static String ObjectToJson(Object value){
		if(value == null) return null;
		try {
			return getMapperInstance().writeValueAsString(value);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static <T>T JsonToObject(String value,Class<T> c){
		if(StringUtils.isEmpty(value)) return null;
		try {
			return getMapperInstance().readValue(value, c);
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T JsonToCollection(String content,Class<T> collectionClass,Class<?>... elementClasses){
		if(StringUtils.isEmpty(content)) return null;
		JavaType type = getCollectionType(collectionClass, elementClasses);
		try {
			return getMapperInstance().readValue(content, type);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	 public static <T> JavaType getCollectionType(Class<T> collectionClass, Class<?>... elementClasses) {
		 //如果是ArrayList<YourBean>那么使用ObjectMapper 的getTypeFactory().constructParametricType(collectionClass, elementClasses);
		 //如果是HashMap<String,YourBean>那么 ObjectMapper 的getTypeFactory().constructParametricType(HashMap.class,String.class, YourBean.class);
		 return getMapperInstance().getTypeFactory().constructParametricType(collectionClass, elementClasses);   
	}  
}
