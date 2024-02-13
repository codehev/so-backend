package com.codehev.sobackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @author codehev
 * @email 2529799312@qq.com
 * @date 2024-02-10 22:26
 * @description
 */
@Data
@AllArgsConstructor
public class Picture implements Serializable {
    private String title;
    private String url;
    private static final long serialVersionUID = 1L;
}
