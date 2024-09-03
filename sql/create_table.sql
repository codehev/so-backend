# 数据库初始化

-- 创建库
create database if not exists so;

-- 切换库
use so;

-- 用户表
create table if not exists user
(
    id           bigint auto_increment comment 'id' primary key,
    userAccount  varchar(256)                           not null comment '账号',
    userPassword varchar(512)                           not null comment '密码',
    unionId      varchar(256)                           null comment '微信开放平台id',
    mpOpenId     varchar(256)                           null comment '公众号openId',
    userName     varchar(256)                           null comment '用户昵称',
    userAvatar   varchar(1024)                          null comment '用户头像',
    userProfile  varchar(512)                           null comment '用户简介',
    userRole     varchar(256) default 'user'            not null comment '用户角色：user/admin/ban',
    createTime   datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime   datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete     tinyint      default 0                 not null comment '是否删除',
    index idx_unionId (unionId)
) comment '用户' collate = utf8mb4_unicode_ci;

-- 帖子表
create table if not exists post
(
    id         bigint auto_increment comment 'id' primary key,
    title      varchar(512)                       null comment '标题',
    content    text                               null comment '内容',
    tags       varchar(1024)                      null comment '标签列表（json 数组）',
    thumbNum   int      default 0                 not null comment '点赞数',
    favourNum  int      default 0                 not null comment '收藏数',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    isDelete   tinyint  default 0                 not null comment '是否删除',
    index idx_userId (userId)
) comment '帖子' collate = utf8mb4_unicode_ci;

-- 帖子点赞表（硬删除）
create table if not exists post_thumb
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子点赞';

-- 帖子收藏表（硬删除）
create table if not exists post_favour
(
    id         bigint auto_increment comment 'id' primary key,
    postId     bigint                             not null comment '帖子 id',
    userId     bigint                             not null comment '创建用户 id',
    createTime datetime default CURRENT_TIMESTAMP not null comment '创建时间',
    updateTime datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    index idx_postId (postId),
    index idx_userId (userId)
) comment '帖子收藏';


INSERT INTO `user` VALUES (1830948955727446017, 'user', 'c05b6cc8ecbcd5202b171ea3b0768e73', NULL, NULL, '用户1', 'https://avatars.githubusercontent.com/u/83279764?v=4', NULL, 'user', '2024-09-03 20:40:23', '2024-09-03 20:43:11', 0);
INSERT INTO `user` VALUES (1830948984626200578, 'admin', 'c05b6cc8ecbcd5202b171ea3b0768e73', NULL, NULL, '管理员1', 'https://avatars.githubusercontent.com/u/83279764?v=4', NULL, 'admin', '2024-09-03 20:40:29', '2024-09-03 20:43:11', 0);


INSERT INTO post (title, content, tags, thumbNum, favourNum, userId, createTime, updateTime, isDelete)
VALUES
    ('Spring Boot 最佳实践', '本文讨论了在使用 Spring Boot 时应遵循的最佳实践，包括依赖管理、配置和测试策略。', '["Java", "Spring Boot", "最佳实践"]', 120, 45, 101, NOW(), NOW(), 0),

    ('使用 Java 构建 RESTful API', '学习如何使用 Java 和 Spring MVC 设计和实现 RESTful API，重点介绍正确的错误处理和数据验证。', '["Java", "REST API", "Spring MVC"]', 85, 30, 102, NOW(), NOW(), 0),

    ('Java 中的数据库连接池', '探讨在 Java 应用中使用连接池的好处，并介绍如何使用 HikariCP 在 Spring Boot 中进行配置。', '["Java", "数据库", "连接池", "HikariCP"]', 95, 40, 103, NOW(), NOW(), 0),

    ('Java 应用性能优化', '本文提供了优化 Java 应用性能的技巧和方法，重点关注垃圾收集、内存管理和并发处理。', '["Java", "性能", "优化"]', 110, 50, 104, NOW(), NOW(), 0),

    ('Spring Cloud 微服务入门', '介绍了使用 Spring Cloud 构建微服务架构的基本概念和实现方法，包括服务注册、配置管理和负载均衡。', '["Java", "Spring Cloud", "微服务"]', 130, 60, 105, NOW(), NOW(), 0),

    ('Spring Security 授权与认证详解', '本文详细介绍了如何在 Spring Boot 应用中实现授权与认证，包括使用 JWT 和 OAuth2 进行安全控制。', '["Java", "Spring Security", "授权", "认证"]', 150, 70, 106, NOW(), NOW(), 0),

    ('JPA 与 Hibernate 实战', '学习如何在 Java 应用中使用 JPA 和 Hibernate 进行持久化操作，涵盖了常见的配置和性能优化技巧。', '["Java", "JPA", "Hibernate", "持久化"]', 140, 55, 107, NOW(), NOW(), 0),

    ('Spring Boot 中的异步编程', '探讨在 Spring Boot 中实现异步编程的方法，包括使用 @Async 注解和 CompletableFuture 的应用场景。', '["Java", "Spring Boot", "异步编程", "CompletableFuture"]', 115, 40, 108, NOW(), NOW(), 0),

    ('Redis 在 Java 项目中的应用', '本文介绍了如何在 Java 后端项目中使用 Redis 进行缓存管理和数据存储，重点讲解了 RedisTemplate 的使用。', '["Java", "Redis", "缓存", "数据存储"]', 125, 50, 109, NOW(), NOW(), 0),

    ('分布式系统中的事务处理', '探讨在分布式系统中实现事务处理的几种方式，包括 TCC、SAGA 模式，以及在 Spring Cloud 中的应用。', '["Java", "分布式系统", "事务处理", "TCC", "SAGA"]', 135, 65, 110, NOW(), NOW(), 0),

    ('Java 中的多线程与并发编程', '本文介绍了 Java 中多线程与并发编程的基础知识，包括线程池、锁机制，以及常见的并发工具类。', '["Java", "多线程", "并发编程", "线程池"]', 160, 80, 111, NOW(), NOW(), 0),

    ('Spring Boot 中的日志管理', '了解如何在 Spring Boot 应用中进行日志管理，使用 Logback 和 SLF4J 实现日志的集中化处理与监控。', '["Java", "Spring Boot", "日志管理", "Logback", "SLF4J"]', 100, 35, 112, NOW(), NOW(), 0),

    ('使用 MyBatis 实现动态 SQL', '学习如何使用 MyBatis 实现动态 SQL 查询，包括 XML 配置方式和注解方式的详细讲解。', '["Java", "MyBatis", "动态 SQL", "数据库"]', 145, 60, 113, NOW(), NOW(), 0),

    ('Java 应用中的消息队列实践', '探讨在 Java 后端应用中使用消息队列的场景，包括 RabbitMQ 和 Kafka 的应用与配置。', '["Java", "消息队列", "RabbitMQ", "Kafka"]', 155, 75, 114, NOW(), NOW(), 0),

    ('Spring Boot 中的测试驱动开发 (TDD)', '介绍在 Spring Boot 项目中如何使用 JUnit 和 Mockito 实现测试驱动开发，确保代码质量和可靠性。', '["Java", "Spring Boot", "TDD", "JUnit", "Mockito"]', 110, 45, 115, NOW(), NOW(), 0);
