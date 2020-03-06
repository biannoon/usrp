package com.scrcu.common.taglib.bean;

public class BasePageBean {
    private int currentPage; //当前页，默认为第1页
    private int pageSize; //每页记录数，默认为10
    private int totalSize; //总记录数，数据库查询集合中size
    private String url; //跳转的url

    /**
     * 构造函数
     * @param currentPage
     * @param pageSize
     * @param totalSize
     */
    public BasePageBean(int currentPage, int pageSize, int totalSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalSize = totalSize;
    }

    /**
     * 获取总页数
     * @return
     */
    public int getPageCount(){
        int pageCount = 0;
        if (this.totalSize % this.pageSize != 0)
            pageCount = this.totalSize / this.pageSize + 1;
        else
            pageCount = this.totalSize / this.pageSize;
        return pageCount;
    }

    /**
     * 获取最后一页的记录数
     * @return
     */
    public int getLastPageSize(){
        int lastPageSize = 0;
        if (this.totalSize % this.pageSize == 0)
            lastPageSize = this.pageSize;
        else
            lastPageSize = this.totalSize % this.pageSize;
        return lastPageSize;
    }

    /**
     * 获取当前页
     * @return
     */
    public int getCurrentPage(){
        return currentPage;
    }

    /**
     * 获取总记录数
     * @return
     */
    public int getTotalSize(){
        return totalSize;
    }

    /**
     * 获取每页记录数
     * @return
     */
    public int getPageSize(){
        return pageSize;
    }

    /**
     * 获取访问地址
     * @return
     */
    public String getUrl(){
        return url;
    }

    /**
     * 获取上一页
     * @return
     */
    public int getPreviousPage(){
        if (this.getCurrentPage() == 1)
            return 0;
        else
            return this.getCurrentPage() - 1;
    }

    /**
     * 获取下一页
     * @return
     */
    public int getNextPage(){
        if (this.getCurrentPage() == this.getPageCount())
            return 0;
        else
            return this.getCurrentPage() + 1;
    }


    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public void setUrl(String url) {
        this.url = url;
    }




}
