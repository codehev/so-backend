package com.codehev.sobackend.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-13 17:19
 * @description 聚合搜索结果
 */
@Data
public class SearchVO implements Serializable {
    private List<PostVO> postVOList;
    private List<Picture> pictureList;
    private List<UserVO> userVOList;
    private List<Object> dataList;

    private static final long serialVersionUID = 1L;
}
