package com.examw.collector.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;

import com.examw.collector.domain.LoginLog;
import com.examw.model.IPaging;
/**
 * 登录日志信息。
 * @author yangyong.
 * @since 2014-05-17.
 */
@JsonSerialize(include = Inclusion.NON_NULL)
public class LoginLogInfo extends LoginLog implements IPaging {
	private static final long serialVersionUID = 1L;
	private Integer rows,page;
	private String sort,order;
	/**
	 * 获取每页数据。
	 * @return
	 * 每页数据。
	 */
	@Override
	public Integer getRows() {
		return this.rows;
	}
	/**
	 * 设置每页数据。
	 * @param rows
	 * 每页数据。
	 */
	@Override
	public void setRows(Integer rows) {
		this.rows = rows;
	}
	/**
	 * 获取当前页码。
	 * @return
	 * 当前页码。
	 */
	@Override
	public Integer getPage() {
		return this.page;
	}
	/**
	 * 设置当前页码。
	 * @param page
	 * 当前页码。
	 */
	@Override
	public void setPage(Integer page) {
		this.page = page;
	}
	/**
	 * 获取排序字段。
	 * @return
	 * 	排序字段。
	 */
	@Override
	public String getSort() {
		return this.sort;
	}
	/**
	 *  设置排序字段。
	 * @param sort
	 * 排序字段。
	 */
	@Override
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * 获取排序。
	 * @return
	 * 排序。
	 */
	@Override
	public String getOrder() {
		return this.order;
	}
	/**
	 * 设置排序。
	 * @param order
	 * 排序。
	 */
	@Override
	public void setOrder(String order) {
		 this.order = order;
	}
}