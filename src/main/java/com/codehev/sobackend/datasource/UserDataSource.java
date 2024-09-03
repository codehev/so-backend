package com.codehev.sobackend.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.model.dto.post.PostQueryRequest;
import com.codehev.sobackend.model.dto.user.UserQueryRequest;
import com.codehev.sobackend.model.vo.PostVO;
import com.codehev.sobackend.model.vo.UserVO;
import com.codehev.sobackend.service.PostService;
import com.codehev.sobackend.service.UserService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-14 15:10
 * @description
 */
@Component
public class UserDataSource implements DataSource<UserVO>{
    @Resource
    private UserService userService;
    @Override
    public Page<UserVO> doSearch(String searchText, Long current, Long pageSize) {
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText);
        userQueryRequest.setCurrent(current);
        userQueryRequest.setPageSize(pageSize);

        //非controller层获取request对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        Page<UserVO> userVOPage = userService.listUserVOByPage(userQueryRequest, request);
        return userVOPage;
    }
}
