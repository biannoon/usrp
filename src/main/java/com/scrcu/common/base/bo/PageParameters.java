package com.scrcu.common.base.bo;

/**
 * <p>
 * 分页信息对象
 * </p>
 *
 * @author wuyu
 * @version v1.0
 * @date 2019/9/6 16:35
 **/
public class PageParameters {
	/**
	 * 页数
	 **/
	private int page;
	/**
	 * 行数
	 **/
	private int rows;

	//private static final String PAGE = "page";
	//private static final String ROWS = "rows";

	public PageParameters() {}

	public PageParameters(int page, int rows) {
		this.page = page;
		this.rows = rows;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
}
