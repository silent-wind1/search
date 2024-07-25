package com.yefeng.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public interface DataSource<T> {

    /**
     * 搜索
     *
     * @param searchText
     * @param pageNum
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, long pageNum, long pageSize);

}