package com.examw.collector.model;

import com.examw.collector.domain.AdVideo;
import com.examw.model.IPaging;

/**
 * 
 * @author fengwei.
 * @since 2014年6月30日 上午11:32:44.
 */
public class AdVideoInfo extends AdVideo implements IPaging{
	private static final long serialVersionUID = 1L;

	@Override
	public Integer getRows() {
		
		return null;
	}

	@Override
	public void setRows(Integer rows) {
	}

	@Override
	public Integer getPage() {
		
		return null;
	}

	@Override
	public void setPage(Integer page) {
	}

	@Override
	public String getSort() {
		
		return null;
	}

	@Override
	public void setSort(String sort) {
	}

	@Override
	public String getOrder() {
		
		return null;
	}

	@Override
	public void setOrder(String order) {
	}

}
