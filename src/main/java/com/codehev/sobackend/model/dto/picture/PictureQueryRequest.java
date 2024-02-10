package com.codehev.sobackend.model.dto.picture;

import com.codehev.sobackend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-10 22:21
 * @description
 */
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    private static final long serialVersionUID = 1L;
}
