package com.offcn.util;

import java.io.Serializable;
import java.util.List;

/**
 * 分页结果封装对象
 * @author 13320
 */
public class PageResult implements Serializable{
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 当前页结果
     */
    private List rows;

    /**
     * 分页结果封装对象构造方法
     * @param total 总记录数
     * @param rows 当前页结果
     */
    public PageResult(Long total, List rows) {
        super();
        this.total = total;
        this.rows = rows;
    }
    public Long getTotal() {
        return total;
    }
    public void setTotal(Long total) {
        this.total = total;
    }
    public List getRows() {
        return rows;
    }
    public void setRows(List rows) {
        this.rows = rows;
    }
}
