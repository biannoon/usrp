package com.scrcu.common.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.io.Serializable;
import java.util.List;

/**
 * @描述： 分页工具类
 * @创建人： jiyuanbo
 * @创建时间： 2019/8/31 10:24
 */
public class PageUtils<T> extends Page implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int DEFAULT_PAGE_SIZE = 10;
    // 总记录数
    private int totalCount;
    // 每页记录数
    private int pageSize;
    // 总页数
    private int totalPage;
    // 当前页数
    private int currPage;
    // 列表数据
    private List<?> list;
    //是否有下一页
    private boolean hasNext = false;
    //是否有上一页
    private boolean hasPre = false;

    /*public PageUtils() {
    }*/

    /**
     * @描述： 分页工具
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/31 10:24
     */
    public PageUtils() {
        this(0, DEFAULT_PAGE_SIZE);
    }

    /**
     * @描述： 分页工具
     * @param currPage 当前页数
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/31 10:24
     */
    public PageUtils(int currPage) {
        this(currPage, DEFAULT_PAGE_SIZE);
    }

    /**
     * @描述： 分页工具
     * @param pageSize 每页记录数
     * @param currPage 当前页数
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/31 10:24
     */
    public PageUtils(int currPage, int pageSize) {
        this.currPage = currPage;
        this.pageSize = pageSize;
    }

    /**
     * @描述： 分页工具
     * @param list 列表数据
     * @param totalCount 总记录数
     * @param pageSize 每页记录数
     * @param currPage 当前页数
     * @创建人： jiyuanbo
     * @创建时间： 2019/8/31 10:24
     */
    public PageUtils(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int) Math.ceil((double) totalCount / pageSize);
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrPage() {
        return currPage;
    }

    public void setCurrPage(int currPage) {
        this.currPage = currPage;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }
}
