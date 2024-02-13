package com.codehev.sobackend.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.exception.BusinessException;
import com.codehev.sobackend.exception.ThrowUtils;
import com.codehev.sobackend.model.dto.picture.PictureQueryRequest;
import com.codehev.sobackend.model.dto.post.PostQueryRequest;
import com.codehev.sobackend.model.dto.search.SearchRequest;
import com.codehev.sobackend.model.dto.user.UserQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.model.enums.SearchTypeEnum;
import com.codehev.sobackend.model.vo.PostVO;
import com.codehev.sobackend.model.vo.SearchVO;
import com.codehev.sobackend.model.vo.UserVO;
import com.codehev.sobackend.service.PictureService;
import com.codehev.sobackend.service.PostService;
import com.codehev.sobackend.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-14 0:31
 * @description 聚合搜索（门面模式实现）
 */
@Component
public class SearchFacade {

    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(searchRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        String searchText = searchRequest.getSearchText();
        String type = searchRequest.getType();
        //type对象为空，或空字符串
        ThrowUtils.throwIf(StringUtils.isBlank(type), ErrorCode.PARAMS_ERROR);
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);

        // 1. type不为空，判断type是否合法
        if (searchTypeEnum == null) {
            PostQueryRequest postQueryRequest = new PostQueryRequest();
            postQueryRequest.setSearchText(searchText);
            postQueryRequest.setCurrent(current);
            postQueryRequest.setPageSize(pageSize);
            Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);

            PictureQueryRequest pictureQueryRequest = new PictureQueryRequest();
            pictureQueryRequest.setSearchText(searchText);
            pictureQueryRequest.setCurrent(current);
            pictureQueryRequest.setPageSize(pageSize);
            Page<Picture> picturePage = pictureService.searchPictures(pictureQueryRequest);

            UserQueryRequest userQueryRequest = new UserQueryRequest();
            userQueryRequest.setUserName(searchText);
            userQueryRequest.setCurrent(current);
            userQueryRequest.setPageSize(pageSize);
            Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest, request);

            SearchVO searchVO = new SearchVO();
            searchVO.setPostVOList(postVOPage.getRecords());
            searchVO.setPictureList(picturePage.getRecords());
            searchVO.setUserVOList(userVOPage.getRecords());

            return searchVO;
        }

        // 2. type合法，根据type查询数据
        SearchVO searchVO = new SearchVO();
        switch (searchTypeEnum) {
            case POST:
                PostQueryRequest postQueryRequest = new PostQueryRequest();
                postQueryRequest.setSearchText(searchText);
                postQueryRequest.setCurrent(current);
                postQueryRequest.setPageSize(pageSize);
                Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest, request);
                searchVO.setPostVOList(postVOPage.getRecords());
                break;
            case PICTURE:
                PictureQueryRequest pictureQueryRequest = new PictureQueryRequest();
                pictureQueryRequest.setSearchText(searchText);
                pictureQueryRequest.setCurrent(current);
                pictureQueryRequest.setPageSize(pageSize);
                Page<Picture> picturePage = pictureService.searchPictures(pictureQueryRequest);
                searchVO.setPictureList(picturePage.getRecords());
                break;
            case USER:
                UserQueryRequest userQueryRequest = new UserQueryRequest();
                userQueryRequest.setUserName(searchText);
                userQueryRequest.setCurrent(current);
                userQueryRequest.setPageSize(pageSize);
                Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest, request);
                searchVO.setUserVOList(userVOPage.getRecords());
                break;
            default:
                break;
        }
        return searchVO;
    }
}
