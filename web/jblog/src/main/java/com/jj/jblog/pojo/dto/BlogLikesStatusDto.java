package com.jj.jblog.pojo.dto;

import lombok.Data;
/**
 * 博客点赞消息封装
 * @author 任人子
 * @date 2022/4/9  - {TIME}
 */
@Data
public class BlogLikesStatusDto {

    /**
     * 博客Id
     */
    Long blogId;

    /**
     * 博客点赞数
     */
    Long blogLikes;

    /**
     * 1：已点赞 0：未点赞
     */
    Integer likeStatus;


}
