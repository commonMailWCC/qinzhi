package com.qinzhi.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * mybatis分页
 */
public class Pageable<T> implements Serializable {
    private static final long serialVersionUID = 5721364189263076294L;

    private int pageSize;
    private int total = 0;
    private int totalPage = 1;
    private int pageNo = 1;
    private List<T> rows = Collections.emptyList();

    public Pageable<T> instanceByPageNo(List<T> data, int total, int pageNumber, int pageSize)
    {
        this.pageSize = pageSize;
        this.total = total;
        this.pageNo = pageNumber;
        if (data != null)
            this.rows = data;

        return this;
    }

    public Pageable<T> instanceByStartRow(List<T> data, int total, int startRow, int offset)
    {
        if (offset != 0) {
            this.pageNo = (startRow / offset + 1);
            this.pageSize = offset;
            this.totalPage = (total / offset + 1);
        } else {
            this.pageNo = 1;
            this.pageSize = total;
            this.totalPage = 1;
        }
        if (data != null)
            this.rows = data;

        this.total = total;
        return this;
    }

    public int getStartRow() {
        return ((this.pageNo - 1) * this.pageSize);
    }

    public int getOffset() {
        return this.pageSize;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public int getTotal() {
        return this.total;
    }

    public int getTotalPage() {
        return this.totalPage;
    }

    public int getPageNo() {
        return this.pageNo;
    }

    public int getPageNumber() {
        return this.pageNo;
    }

    public List<T> getData() {
        return this.rows;
    }

    public List<T> getRows() {
        return this.rows;
    }
}
