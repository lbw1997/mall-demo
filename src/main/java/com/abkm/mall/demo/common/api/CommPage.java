package com.abkm.mall.demo.common.api;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * description: 分页数据封装类 <br>
 * date: 2020/9/13 20:33 <br>
 * author: libowen <br>
 * version: 1.0 <br>
 */
public class CommPage<T> {

    private Integer pageNum;
    private Integer pageTotal;
    private Integer pageSize;
    private Long total;
    private List<T> list;

    /**
     * 将MyBatis Plus 分页结果转化为通用结果
     */
    public static <T> CommPage<T> restPage(Page<T> resultPage) {
        CommPage<T> result = new CommPage<>();
        result.setPageNum(Convert.toInt(result.getPageNum()));
        result.setPageSize(Convert.toInt(result.getPageSize()));
        result.setPageTotal(Convert.toInt(result.getPageTotal()));
        result.setTotal(Convert.toLong(result.getTotal()));
        result.setList(resultPage.getRecords());
        return result;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
