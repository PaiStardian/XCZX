package com.xuecheng.base.model;

import lombok.Data;
import lombok.ToString;
/**
 *
 * */
@Data
@ToString
public class PageParams {
    //当前页码
    private Long pageNo = 1L;
    //每页记录数
    private Long pageSize = 1L;

    public PageParams() {
    }

    public PageParams(Long pageNo, Long pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }
}
