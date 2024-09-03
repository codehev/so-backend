package com.codehev.sobackend.job.cycle;

import com.codehev.sobackend.esdao.PostEsDao;
import com.codehev.sobackend.model.dto.post.PostEsDTO;
import com.codehev.sobackend.model.entity.Post;
import com.codehev.sobackend.mapper.PostMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author codehev
 * @email codehev@qq.com
 * @date 2024-2-10
 * @description 增量（最近更新的数据）同步帖子到 es
 */
// todo 取消注释开启任务
@Component
@Slf4j
public class IncSyncPostToEs {

    @Resource
    private PostMapper postMapper;

    @Resource
    private PostEsDao postEsDao;

    /**
     * 每分钟执行一次
     *
     * @Scheduled(initialDelay =  1000, fixedDelay = 100000*1000)
     * initialDelay当任务启动后，等多久开始执行方法,单位毫秒
     * fixedDelay每隔多久执行,单位毫秒
     */
    @Scheduled(fixedRate = 60 * 1000)
    public void run() {
        // 查询近 5 分钟内的数据
//        Date fiveMinutesAgoDate = new Date(new Date().getTime() - 5 * 60 * 1000L);
        //System.currentTimeMillis()以毫秒为单位返回当前时间
        Date fiveMinutesAgoDate = new Date(System.currentTimeMillis() - 5 * 60 * 1000L);
        List<Post> postList = postMapper.listPostWithDelete(fiveMinutesAgoDate);
        if (CollectionUtils.isEmpty(postList)) {
            log.info("no inc post");
            return;
        }
        List<PostEsDTO> postEsDTOList = postList.stream()
                .map(PostEsDTO::objToDto)
                .collect(Collectors.toList());
        final int pageSize = 500;
        int total = postEsDTOList.size();
        log.info("IncSyncPostToEs start, total {}", total);
        for (int i = 0; i < total; i += pageSize) {
            int end = Math.min(i + pageSize, total);
            log.info("sync from {} to {}", i, end);
            postEsDao.saveAll(postEsDTOList.subList(i, end));
        }
        log.info("IncSyncPostToEs end, total {}", total);
    }
}
