package com.codehev.sobackend.datasource;

import com.codehev.sobackend.model.enums.SearchTypeEnum;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-17 22:05
 * @description
 */
@Component
public class DataSourceRegister {
    @Resource
    private PostDataSource postDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;

    private HashMap<String, DataSource> typeDataSourceMap;

    /**
     * PostConstruct：当依赖注入完成后用于执行初始化的方法，并且只会被执行一次
     */
    @PostConstruct
    public void init() {
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
        }};
    }

    public DataSource getDataSourceByType(String type) {
        if (typeDataSourceMap == null) {
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
