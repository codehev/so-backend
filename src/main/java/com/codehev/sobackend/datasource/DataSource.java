package com.codehev.sobackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-14 15:07
 * @description 适配器模式定义：将一个类的接口转换成客户希望的另外一个接口，使得原本由于接口不兼容而不能一起工作的那些类能一起工作。
 * 当接口参数不一致时使用
 * 把原来的各种数据源的service的搜索方法分别单独提出来， 改成实现这个接口
 * 因为要做搜索中台，所以把原来的各种数据源的service的搜索方法（例如listPostVOByPage）分别单独提出来，不应该与内部系统绑定一起
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
