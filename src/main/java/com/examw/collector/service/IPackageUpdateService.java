package com.examw.collector.service;

import java.util.List;

import com.examw.collector.model.PackInfo;

/**
 * 
 * @author fengwei.
 * @since 2014年7月9日 下午5:01:14.
 */
public interface IPackageUpdateService {
	/**
	 * 更新集合
	 * @param info
	 */
	void update(List<PackInfo> packs);
}
