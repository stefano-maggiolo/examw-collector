package com.examw.collector.support;
import java.io.IOException;
import java.net.ConnectException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.X509TrustManager;

import org.apache.log4j.Logger;
/**
 * HTTP访问工具类。
 * @author yangyong.
 * @since 2014-02-25.
 * */
public final class HttpUtil {
	private static Logger logger = Logger.getLogger(HttpUtil.class);
	/**
	 * 发起https请求并获取结果。
	 * @param mgr
	 * 	SSL证书。
	 * @param requestUrl
	 * 	请求地址。
	 * @param requestMethod
	 * 	请求方式(GET,POST)。
	 * @param data
	 *  提交数据。
	 *  @return 反馈结果。
	 * */
	public static String httpsRequest(X509TrustManager mgr, String requestUrl, String requestMethod, String data){
		try {
			logger.info("url:\r\n"+ requestUrl);
			logger.info("method:\r\n" + requestMethod);
			logger.info("data:\r\n"+ data);
			
			String result = com.examw.utils.HttpUtil.sendRequest(mgr, requestUrl, requestMethod, data);
			 
			logger.info("callback:\r\n" + result);
			 
			return result;
		}catch(ConnectException e){
			logger.error("连接服务器["+ requestUrl +"]异常：", e);
		} catch (Exception e) {
			logger.error("https请求异常：",e);
		}
		return null;
	}
	/**
	 * 发起https请求并获取结果。
	 * @param requestUrl
	 * 	请求地址。
	 * @param requestMethod
	 * 	请求方式(GET,POST)。
	 * @param data
	 *  提交数据。
	 *  @return 反馈结果。
	 * */
	public static String httpsRequest(String requestUrl, String requestMethod, String data){
		return httpsRequest(new X509TrustManager(){
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
			@Override
			public X509Certificate[] getAcceptedIssuers() {  return null; }
		}, requestUrl, requestMethod, data);
	}
	/**
	 * 发起http请求并获取结果。
	 * @param requestUrl
	 * 	请求地址。
	 * @param requestMethod
	 * 	请求方式(GET,POST)。
	 * @param data
	 *  提交数据。
	 * @param charsetName
	 * 字符集。
	 *  @return 反馈结果.
	 * @throws IOException 
	 * */
	public static String httpRequest(String requestUrl, String requestMethod, String data,String charsetName) throws IOException{
		try {
			logger.info("url:\r\n"+ requestUrl);
			logger.info("method:\r\n" + requestMethod);
			logger.info("data:\r\n"+ data);
			
			String result = com.examw.utils.HttpUtil.sendRequest(requestUrl, requestMethod, data, charsetName);
			 
			logger.info("callback:\r\n" + result);
			return result;
		}catch(ConnectException e){
			logger.error("连接服务器["+ requestUrl +"]异常：", e);
		}
		return null;
	}
	/**
	 * 发起http请求并获取结果。
	 * @param requestUrl
	 * 	请求地址。
	 * @param requestMethod
	 * 	请求方式(GET,POST)。
	 * @param data
	 *  提交数据。
	 *  @return 反馈结果.
	 * @throws IOException 
	 * */
	public static String httpRequest(String requestUrl, String requestMethod, String data) throws IOException{
		return httpRequest(requestUrl, requestMethod, data, null);
	}
}