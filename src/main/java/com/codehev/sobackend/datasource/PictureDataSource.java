package com.codehev.sobackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.model.dto.picture.PictureQueryRequest;
import com.codehev.sobackend.model.dto.post.PostQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.model.vo.PostVO;
import com.codehev.sobackend.service.PictureService;
import com.codehev.sobackend.service.PostService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-14 15:10
 * @description
 */
@Component
public class PictureDataSource implements DataSource<Picture>{
    @Resource
    private PictureService pictureService;
    @Override
    public Page<Picture> doSearch(String searchText, Long current, Long pageSize) {
        PictureQueryRequest pictureQueryRequest = new PictureQueryRequest();
        pictureQueryRequest.setSearchText(searchText);
        pictureQueryRequest.setCurrent(current);
        pictureQueryRequest.setPageSize(pageSize);
        Page<Picture> picturePage = pictureService.searchPictures(pictureQueryRequest);
        return picturePage;
    }
}
