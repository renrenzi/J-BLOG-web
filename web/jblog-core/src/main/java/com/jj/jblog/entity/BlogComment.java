package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 博客评论表
 * @author 任人子
 * @date 2021/11/14  - {TIME}
 */
@TableName(value = "tb_blog_comment")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogComment implements Serializable {
    @TableId(value = "comment_id", type = IdType.AUTO)
    private Long commentId;

    private Long blogId;

    private String commentator;

    private String email;

    private String websiteUrl;

    private String commentBody;

    private Date commentCreateTime;

    private String commentatorIp;

    private String replyBody;

    private Date replyCreateTime;

    private Integer commentStatus;

    private Integer isDeleted;

}
