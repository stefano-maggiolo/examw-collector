package com.examw.collector.domain;

import java.io.Serializable;
/**
 * 广告视频。
 * @author yangyong.
 * @since 2014-06-27.
 */
public class AdVideo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code,name,address;
	private Integer type;
	/**
	 * 获取代码。
	 * @return 代码。
	 */
	public String getCode() {
		return code;
	}
	/**
	 * 获取代码。
	 * @param code
	 * 代码。
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * 获取类型。
	 * @return 类型。
	 */
	public Integer getType() {
		return type;
	}
	/**
	 * 设置类型。
	 * @param type
	 * 类型。
	 */
	public void setType(Integer type) {
		this.type = type;
	}
	/**
	 * 获取名称。
	 * @return 名称。
	 */
	public String getName() {
		return name;
	}
	/**
	 * 设置名称。
	 * @param name
	 * 名称。
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 获取链接。
	 * @return 链接。
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * 设置链接。
	 * @param address
	 * 链接。
	 */
	public void setAddress(String address) {
		this.address = address;
	}
}