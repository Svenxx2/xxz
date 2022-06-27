package com.bjpn.money.utils;

import java.io.Serializable;

/**共20条3页。上一页下一页 首页尾页
 *   变：上一页  下一页
 *   不变：总页数  总记录数 首页  尾页 一页多少条数据
 *
 */



public class PageModel implements Serializable {
    private Integer firstPage = 1; //首页
    private long cunPage; //当前页
    private Integer pageSize ;  //一页多少数据
    private long totalPage; //总页数 最后一页
    private long totalCount;//总记录数

    public PageModel(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public PageModel() {
    }

    public Integer getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(Integer firstPage) {
        this.firstPage = firstPage;
    }

    public long getCunPage() {
        return cunPage;
    }

    public void setCunPage(long cunPage) {
        this.cunPage = cunPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public long getTotalPage() {
        return totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}
