package com.codehev.sobackend.esdao;

import com.codehev.sobackend.model.dto.post.PostEsDTO;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * 帖子 ES 操作
 *
 * @author codehev
 */
public interface PostEsDao extends ElasticsearchRepository<PostEsDTO, Long> {
    /**
     * 根据用户ID查询帖子
     *
     * @param userId
     * @return
     */
    List<PostEsDTO> findByUserId(Long userId);
}