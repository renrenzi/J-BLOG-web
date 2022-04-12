package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author 张俊杰
 * @date 2021/11/5  - {TIME}
 */
@TableName(value = "tb_blog_info")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogInfo {

    /**
     * 博客表主键id
     */
    @TableId(value = "blog_id",type = IdType.AUTO)
    private Long blogId;

    /**
     * 博客标题
     */
    private String  blogTitle;

    /**
     * 博客自定义路径url
     */
    private String  blogSubUrl ;

    /**
     * 博客前言
     */
    private String  blogPreface;

    /**
     * 博客内容
     */
    private String blogContent;


    /**
     * 博客分类id
     */
    private Integer blogCategoryId;

    /**
     * 博客分类(冗余字段)
     */
    private String blogCategoryName;

    /**
     * 博客标签(冗余字段)
     */
    private String blogTags;

    /**
     * 0-草稿 1-发布
     */
    private Integer blogStatus;

    /**
     * 阅读量
     */
    private Long blogViews;

    /**
     * 点赞数
     */
    private Long blogLikes;
    /**
     * 0-允许评论 1-不允许评论
     */
    private Integer enableComment;

    /**
     * 是否删除 0=否 1=是
     */
    private Integer isDeleted;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;





}
