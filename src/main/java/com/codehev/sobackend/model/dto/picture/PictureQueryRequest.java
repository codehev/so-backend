package com.codehev.sobackend.model.dto.picture;

import com.codehev.sobackend.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-10 22:21
 * @description
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PictureQueryRequest extends PageRequest implements Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    private static final long serialVersionUID = 1L;
}
