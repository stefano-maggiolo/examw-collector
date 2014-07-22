package com.examw.collector.service.impl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.examw.collector.dao.IOperateLogDao;
import com.examw.collector.domain.OperateLog;
import com.examw.collector.model.OperateLogInfo;
import com.examw.collector.model.PackInfo;
import com.examw.collector.model.SubClassInfo;
import com.examw.collector.service.IOperateLogService;
import com.examw.collector.support.ExcelStyleUtils;
import com.examw.collector.support.JSONUtil;
import com.examw.model.DataGrid;

/**
 * 登录日志服务实现。
 * 
 * @author yangyong.
 * @since 2014-05-17.
 */
public class OperateLogServiceImpl extends
		BaseDataServiceImpl<OperateLog, OperateLogInfo> implements
		IOperateLogService {
	private IOperateLogDao operateLogDao;
	private Map<String, String> typeMap;

	/**
	 * 设置登录日志数据接口。
	 * 
	 * @param loginLogDao
	 *            数据接口。
	 */
	public void setOperateLogDao(IOperateLogDao operateLogDao) {
		this.operateLogDao = operateLogDao;
	}

	/**
	 * 获取 类型名称映射
	 * 
	 * @return typeMap
	 * 
	 */
	public Map<String, String> getTypeMap() {
		return typeMap;
	}

	/**
	 * 设置 类型名称映射
	 * 
	 * @param typeMap
	 * 
	 */
	public void setTypeMap(Map<String, String> typeMap) {
		this.typeMap = typeMap;
	}

	/*
	 * 添加登录日志。
	 */
	@Override
	public void addLog(String account, String ip, String browser) {
		if (!StringUtils.isEmpty(account)) {
			OperateLog data = new OperateLog();
			data.setId(UUID.randomUUID().toString());
			data.setAccount(account);
			data.setAddTime(new Date());
			this.operateLogDao.save(data);
		}
	}

	/*
	 * 查询数据。
	 */
	@Override
	protected List<OperateLog> find(OperateLogInfo info) {
		return this.operateLogDao.findOperateLogs(info);
	}

	/*
	 * 类型转换。
	 */
	@Override
	protected OperateLogInfo changeModel(OperateLog data) {
		if (data == null)
			return null;
		OperateLogInfo info = new OperateLogInfo();
		BeanUtils.copyProperties(data, info);
		info.setTypeName(this.getTypeName(data.getType()));
		return info;
	}

	/*
	 * 查询数据统计。
	 */
	@Override
	protected Long total(OperateLogInfo info) {
		return this.operateLogDao.total(info);
	}

	/*
	 * 更新数据。
	 */
	@Override
	public OperateLogInfo update(OperateLogInfo info) {
		if (info == null)
			return null;
		boolean isAdded = false;
		OperateLog data = StringUtils.isEmpty(info.getId()) ? null
				: this.operateLogDao.load(OperateLog.class, info.getId());
		if (isAdded = (data == null)) {
			if (StringUtils.isEmpty(info.getId())) {
				info.setId(UUID.randomUUID().toString());
			}
			data = new OperateLog();
		}
		BeanUtils.copyProperties(info, data);
		if (isAdded)
			this.operateLogDao.save(data);
		return info;
	}

	/*
	 * 删除数据。
	 */
	@Override
	public void delete(String[] ids) {
		if (ids == null || ids.length == 0)
			return;
		for (int i = 0; i < ids.length; i++) {
			if (StringUtils.isEmpty(ids[i]))
				continue;
			OperateLog data = this.operateLogDao.load(OperateLog.class, ids[i]);
			if (data != null)
				this.operateLogDao.delete(data);
		}
	}

	@Override
	public String getTypeName(Integer type) {
		if (typeMap == null || type == null)
			return null;
		return this.typeMap.get(type.toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> DataGrid<T> datagridForShow(OperateLogInfo info, Class<T> c) {
		if (StringUtils.isEmpty(info.getId()))
			return null;
		DataGrid<T> grid = new DataGrid<T>();
		List<T> rows = new ArrayList<T>();
		try {
			OperateLog log = this.operateLogDao.load(OperateLog.class,
					info.getId());
			if (log != null) {
				rows = JSONUtil.JsonToCollection(log.getContent(),
						ArrayList.class, c);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		grid.setRows(rows);
		grid.setTotal((long) rows.size());
		return grid;
	}

	@Override
	public void getExcel(OperateLogInfo info, OutputStream out) {
		// 根据条件查询
		OperateLog log = this.operateLogDao
				.load(OperateLog.class, info.getId());
		if (log == null)
			return;
		// 把数据放入输出流
		putDataOnOutputStream(out, log);
	}

	// 封装了将数据放入输出流的方法
	@SuppressWarnings("unchecked")
	private void putDataOnOutputStream(OutputStream os, OperateLog log) {
		jxl.write.Label label;
		WritableWorkbook book = null;
		try {
			book = Workbook.createWorkbook(os);
			WritableSheet sheet = null;
			List<SubClassInfo> gradeList = null;
			List<PackInfo> packList = null;
			if (log.getType().equals(OperateLog.TYPE_UPDATE_GRADE)) {
				sheet = book.createSheet("班级比对更新记录列表", 0);
				gradeList = JSONUtil.JsonToCollection(log.getContent(),
						ArrayList.class, SubClassInfo.class);
			} else if (log.getType().equals(OperateLog.TYPE_UPDATE_PACKAGE)) {
				sheet = book.createSheet("套餐比对更新记录列表", 0);
				packList = JSONUtil.JsonToCollection(log.getContent(),
						ArrayList.class, PackInfo.class);
			} else
				return;
			if ((gradeList == null || gradeList.size() == 0)
					&& (packList == null || packList.size() == 0))
				return;
			// 设置各列宽度
			// sheet.setColumnView(0, 10); //编号
			sheet.setColumnView(0, 30); // 卡号 id
			sheet.setColumnView(1, 15); // 密码 name
			// sheet.setColumnView(3, 25); //生成时间
			sheet.setColumnView(2, 15); // 状态
			sheet.setColumnView(3, 100); // 变更提醒
			// 设置行高
			sheet.setRowView(0, 500);
			sheet.setRowView(1, 500);
			// 第一行
			sheet.mergeCells(0, 0, 10, 0);
			label = new Label(0, 0, "比对更新列表表格");

			label.setCellFormat(ExcelStyleUtils
					.titleCellFormat(null, false, 16));
			sheet.addCell(label);
			// 第二行
			sheet.mergeCells(0, 1, 10, 1);
			Label line2 = new Label(0, 1, log.getAddTime().toString());
			line2.setCellFormat(ExcelStyleUtils.titleCellFormat(
					Alignment.RIGHT, false, 14));
			sheet.addCell(line2);
			// 第三行
			// sheet.addCell(new Label(0, 2, "编号",
			// ExcelStyleUtils.titleCellFormat(null, true, 12)));
			sheet.addCell(new Label(0, 2, "代码", ExcelStyleUtils
					.titleCellFormat(null, true, 12)));
			sheet.addCell(new Label(1, 2, "名称", ExcelStyleUtils
					.titleCellFormat(null, true, 12)));
			// sheet.addCell(new Label(3, 2, "生成时间",
			// ExcelStyleUtils.titleCellFormat(null, true, 12)));
			sheet.addCell(new Label(2, 2, "状态", ExcelStyleUtils
					.titleCellFormat(null, true, 12)));
			sheet.addCell(new Label(3, 2, "变更提醒", ExcelStyleUtils
					.titleCellFormat(null, true, 12)));
			// 循环输出内容
			if (gradeList != null) {
				for (int j = 0; j < gradeList.size(); j++) {
					SubClassInfo info = gradeList.get(j);
					// sheet.addCell(new Label(0, j+3, card.getId().toString(),
					// ExcelStyleUtils.contentCellFormat(null, true, 10)));
					sheet.addCell(new Label(0, j + 3, info.getCode(),
							ExcelStyleUtils.contentCellFormat(null, true, 10)));
					sheet.addCell(new Label(1, j + 3, info.getName(),
							ExcelStyleUtils.contentCellFormat(null, true, 10)));
					// sheet.addCell(new Label(3, j+3,
					// card.getCardAddTime().toString(),
					// ExcelStyleUtils.contentCellFormat(null, true, 10)));
					sheet.addCell(new Label(2, j + 3, info.getStatus()
							.toString(), ExcelStyleUtils.contentCellFormat(
							null, true, 10)));
					sheet.addCell(new Label(3, j + 3, info.getUpdateInfo()
							.toString(), ExcelStyleUtils.contentCellFormat(
							null, true, 10)));
				}
			}
			if (packList != null) {
				for (int j = 0; j < packList.size(); j++) {
					PackInfo info = packList.get(j);
					// sheet.addCell(new Label(0, j+3, card.getId().toString(),
					// ExcelStyleUtils.contentCellFormat(null, true, 10)));
					sheet.addCell(new Label(0, j + 3, info.getCode(),
							ExcelStyleUtils.contentCellFormat(null, true, 10)));
					sheet.addCell(new Label(1, j + 3, info.getName(),
							ExcelStyleUtils.contentCellFormat(null, true, 10)));
					// sheet.addCell(new Label(3, j+3,
					// card.getCardAddTime().toString(),
					// ExcelStyleUtils.contentCellFormat(null, true, 10)));
					sheet.addCell(new Label(2, j + 3, info.getStatus()
							.toString(), ExcelStyleUtils.contentCellFormat(
							null, true, 10)));
					sheet.addCell(new Label(3, j + 3, info.getUpdateInfo()
							.toString(), ExcelStyleUtils.contentCellFormat(
							null, true, 10)));
				}
			}
			book.write();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(book!=null){
				try {
					book.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}