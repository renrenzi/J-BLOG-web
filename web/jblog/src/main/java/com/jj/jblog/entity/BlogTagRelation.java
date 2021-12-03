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
 * @date 2021/11/11  - {TIME}
 */
@TableName(value = "tb_blog_tag_relation")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogTagRelation {

    /**
     * 关系表id
     */
    @TableId(value = "relation_id", type = IdType.AUTO)
    private Integer relationId;

    /**
     * 博客id
     */
    private Long blogId;
    /**
     * 标签id
     */
    private Integer tagId;
    /**
     * 添加时间
     */
    private Date createTime;

}
