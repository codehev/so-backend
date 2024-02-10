package com.codehev.sobackend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.codehev.sobackend.model.dto.user.UserQueryRequest;
import com.codehev.sobackend.model.entity.Picture;
import com.codehev.sobackend.model.entity.User;
import com.codehev.sobackend.model.vo.LoginUserVO;
import com.codehev.sobackend.model.vo.UserVO;
import me.chanjar.weixin.common.bean.WxOAuth2UserInfo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户服务
 *
 * @author codehev
 */
public interface PictureService {
    /**
     * 搜索图片
     *
     * @param searchText 搜索文本
     * @param current    当前页
     * @param pageSize   每页条数
     */
    Page<Picture> searchPictures(String searchText, long current, long pageSize);

}
