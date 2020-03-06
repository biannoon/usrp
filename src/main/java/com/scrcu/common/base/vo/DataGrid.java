package com.scrcu.common.base.vo;

import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * easy ui datagrid信息对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:32
 **/
public class DataGrid {
	/**
	 * 总数
	 **/
	private long total = 0L;
	//起始行
	//private int startRow = 1;
	/**
	 * 数据
	 **/
	private List rows = new ArrayList();

	//private List columns = new ArrayList();
	//private List footer = new ArrayList();

	public DataGrid() {}

	public DataGrid(long total, List rows) {
		this.total = total;
		this.rows = rows;
	}

	public DataGrid(IPage<?> iPage) {
		this.total = iPage.getTotal();
		this.rows = iPage.getRecords();
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	/*public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}*/

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}
}
