package com.codehev.sobackend.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.codehev.sobackend.common.ErrorCode;
import com.codehev.sobackend.datasource.*;
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
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-02-14 0:31
 * @description 聚合搜索（门面模式实现）,然后又抽出一层为适配器模式，在此调用适配器（不直接调用service）
 * 适配器模式可达到参数统一
 * 再用注册方式替代原来的Switch语句
 */
@Component
public class SearchFacade {

    @Resource
    private PostService postService;
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;


    @Resource
    private PostDataSource postDataSource;
    @Resource
    private UserDataSource userDataSource;
    @Resource
    private PictureDataSource pictureDataSource;
    @Resource
    private DataSourceRegister dataSourceRegister;

    public SearchVO searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        long current = searchRequest.getCurrent();
        long pageSize = searchRequest.getPageSize();
        String searchText = searchRequest.getSearchText();
        String type = searchRequest.getType();
        SearchTypeEnum searchTypeEnum = SearchTypeEnum.getEnumByValue(type);

        // 1. type对象为空，或空字符串，查询所有
        if (StringUtils.isBlank(type)) {
            CompletableFuture<Page<UserVO>> userTask = CompletableFuture.supplyAsync(() -> {
                Page<UserVO> userVOPage = userDataSource.doSearch(searchText, current, pageSize);
                return userVOPage;
            });
            CompletableFuture<Page<PostVO>> postTask = CompletableFuture.supplyAsync(() -> {
                Page<PostVO> postVOPage = postDataSource.doSearch(searchText, current, pageSize);
                return postVOPage;
            });
            CompletableFuture<Page<Picture>> pictureTask = CompletableFuture.supplyAsync(() -> {
                Page<Picture> picturePage = pictureDataSource.doSearch(searchText, current, pageSize);
                return picturePage;
            });
            //CompletableFuture.allOf组合这些任务、并发执行，并通过join方法开启阻塞等待。
            CompletableFuture.allOf(userTask, postTask, pictureTask).join();

            try {
                Page<UserVO> userVOPage = userTask.get();
                Page<PostVO> postVOPage = postTask.get();
                Page<Picture> picturePage = pictureTask.get();
                SearchVO searchVO = new SearchVO();
                searchVO.setUserVOList(userVOPage.getRecords());
                searchVO.setPostVOList(postVOPage.getRecords());
                searchVO.setPictureList(picturePage.getRecords());
                return searchVO;
            } catch (Exception e) {
                throw new BusinessException(ErrorCode.SYSTEM_ERROR, "查询异常");
            }
        }

        // 2. type不为空且非法
        if (searchTypeEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "type类型非法");
        }

        // 3. type不为空且合法，根据type查询数据
        SearchVO searchVO = new SearchVO();

        //3.1switch方式获取dataSource，相当于if else
        // 缺点是switch语句的每个分支都需要写一个case，如果业务扩张，类型很多，就会堆积很多代码，不优雅
        DataSource dataSource = null;
/*        switch (searchTypeEnum) {
            case POST:
                dataSource = postDataSource;
                break;
            case PICTURE:
                dataSource = pictureDataSource;
                break;
            case USER:
                dataSource = userDataSource;
                break;
            default:
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "type类型非法");
        }*/
        //3.2注册的方式获取dataSource
        dataSource = dataSourceRegister.getDataSourceByType(type);
        Page<?> page = dataSource.doSearch(searchText, current, pageSize);
        searchVO.setDataList(page.getRecords());
        /*switch (searchTypeEnum) {
            case POST:
                searchVO.setPostVOList(page.getRecords());
                break;
            case PICTURE:
                searchVO.setPictureList(page.getRecords());
                break;
            case USER:
                searchVO.setUserVOList(page.getRecords());
                break;
            default:
                searchVO.setDataList(page.getRecords());
        }*/
        return searchVO;
    }
}
