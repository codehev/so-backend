package com.codehev.sobackend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.common.BaseResponse;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.common.ResultUtils;
import com.codehev.sobackend.exception.BusinessException;
import com.codehev.sobackend.exception.ThrowUtils;
import com.codehev.sobackend.model.dto.picture.PictureQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.service.PictureService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
     * 分页获取图片列表
     *
     * @param pictureQueryRequest
     * @return
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest) {
        if (pictureQueryRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        long size = pictureQueryRequest.getPageSize();
        //防止爬虫
        ThrowUtils.throwIf(size > 50, ErrorCode.OPERATION_ERROR, "pageSize不能超过50");
        return ResultUtils.success(pictureService.searchPictures(pictureQueryRequest));
    }
}
