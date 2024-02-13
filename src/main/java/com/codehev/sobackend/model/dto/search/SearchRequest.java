package com.codehev.sobackend.model.dto.search;

import com.codehev.sobackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-13 17:15
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SearchRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    private static final long serialVersionUID = 1L;
}