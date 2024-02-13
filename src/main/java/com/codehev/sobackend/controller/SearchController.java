package com.codehev.sobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.common.BaseResponse;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.common.ResultUtils;
import com.codehev.sobackend.exception.BusinessException;
import com.codehev.sobackend.exception.ThrowUtils;
import com.codehev.sobackend.manager.SearchFacade;
import com.codehev.sobackend.model.dto.picture.PictureQueryRequest;
import com.codehev.sobackend.model.dto.post.PostQueryRequest;
import com.codehev.sobackend.model.dto.search.SearchRequest;
import com.codehev.sobackend.model.dto.user.UserQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.model.entity.Post;
import com.codehev.sobackend.model.enums.SearchTypeEnum;
import com.codehev.sobackend.model.vo.PostVO;
import com.codehev.sobackend.model.vo.SearchVO;
import com.codehev.sobackend.model.vo.UserVO;
import com.codehev.sobackend.service.PictureService;
import com.codehev.sobackend.service.PostService;
import com.codehev.sobackend.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
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
    private SearchFacade searchFacade;

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
        long pageSize = searchRequest.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(pageSize > 20, ErrorCode.PARAMS_ERROR, "pageSize不能超过20");

        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}
