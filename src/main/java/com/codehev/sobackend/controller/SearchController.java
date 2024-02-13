package com.codehev.sobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.common.BaseResponse;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.common.ResultUtils;
import com.codehev.sobackend.exception.BusinessException;
import com.codehev.sobackend.exception.ThrowUtils;
import com.codehev.sobackend.model.dto.picture.PictureQueryRequest;
import com.codehev.sobackend.model.dto.post.PostQueryRequest;
import com.codehev.sobackend.model.dto.search.SearchRequest;
import com.codehev.sobackend.model.dto.user.UserQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.model.entity.Post;
import com.codehev.sobackend.model.vo.PostVO;
import com.codehev.sobackend.model.vo.SearchVO;
import com.codehev.sobackend.model.vo.UserVO;
import com.codehev.sobackend.service.PictureService;
import com.codehev.sobackend.service.PostService;
import com.codehev.sobackend.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 搜索接口
 *
 * @author codehev
 */
@RestController
@RequestMapping("/search")
public class SearchController {
    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;

    /**
     * 一次性查询所有分类数据（不建议分页）
     *
     * @param searchRequest
     * @return
     */
    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        if (ObjectUtils.isEmpty(searchRequest)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String searchText = searchRequest.getSearchText();
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR);


        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText);
        postQueryRequest.setCurrent(current);
        postQueryRequest.setPageSize(pageSize);
        Page<PostVO> postVOPage = postService.listPostVOByPage(postQueryRequest,request);

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

        return ResultUtils.success(searchVO);
    }
}
