package com.codehev.sobackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-14 15:07
 * @description 适配器模式：当接口参数不一致时使用
 */
public interface DataSource<T> {
    /**
     * 搜索
     *
     * @param searchText
     * @param current
     * @param pageSize
     * @return
     */
    Page<T> doSearch(String searchText, Long current, Long pageSize);
}
