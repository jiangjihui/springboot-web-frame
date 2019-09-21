package com.common.web.form;

/**
 * 简单分页查询类
 */
public class PageRequestForm {

    /** 页码 */
    private int pageNum;
    /** 每页大小 */
    private int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
