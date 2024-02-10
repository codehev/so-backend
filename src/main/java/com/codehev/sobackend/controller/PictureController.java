package com.codehev.sobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.annotation.AuthCheck;
import com.codehev.sobackend.common.BaseResponse;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.common.ResultUtils;
import com.codehev.sobackend.constant.UserConstant;
import com.codehev.sobackend.exception.ThrowUtils;
import com.codehev.sobackend.model.dto.picture.PictureQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.service.PictureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 *
 * @author codehev
 */
@RestController
@RequestMapping("/picture")
public class PictureController {

    @Resource
    private PictureService pictureService;


    /**
     * 分页获取图片列表（仅管理员）
     *
     * @param pictureQueryRequest
     * @return
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        long current = pictureQueryRequest.getCurrent();
        long size = pictureQueryRequest.getPageSize();
        //防止爬虫
        ThrowUtils.throwIf(size > 50, ErrorCode.OPERATION_ERROR, "pageSize不能超过50");
        return ResultUtils.success(pictureService.searchPictures(pictureQueryRequest.getSearchText(), current, size));
    }
}
