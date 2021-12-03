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
 * @author 张俊杰
 * @date 2021/11/19  - {TIME}
 */
@TableName(value = "tb_blog_link")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogLink implements Serializable {
    @TableId(value = "link_id",type = IdType.AUTO)
    private Integer linkId;
    private Integer linkType;
    private String linkName;
    private String linkUrl;
    private String linkDescription;
    private Integer linkRank;
    private Integer isDeleted;
    private Date createTime;

}
