package com.example.param;

public abstract class PageParam {

    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE     = 60;
    public static final int DEFAULT_PAGE_NO   = 1;

    protected int           pageNo            = DEFAULT_PAGE_NO;
    protected int           pageSize          = DEFAULT_PAGE_SIZE;
    protected int           offset            = 0;

    public int getOffset() {
        return (getPageNo() - 1) * getPageSize();
    }

    public int getPageNo() {
        if (pageNo < DEFAULT_PAGE_NO) {
            return DEFAULT_PAGE_NO;
        }
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    // 进行不合法计算，防止攻击
    public int getPageSize() {
        if (pageSize < 0 || pageSize > MAX_PAGE_SIZE) {
            return DEFAULT_PAGE_SIZE;
        }
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
}
