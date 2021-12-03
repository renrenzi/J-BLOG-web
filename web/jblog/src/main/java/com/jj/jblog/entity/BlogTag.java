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
 * @date 2021/11/10  - {TIME}
 */
@TableName(value = "tb_blog_tag")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogTag implements Serializable {

    /**
     * 标签表主键id
     */
    @TableId(value = "tag_id", type = IdType.AUTO)
    private Integer tagId;
    /**
     * 标签名称
     */
    private String tagName;
    /**
     * 是否删除 0=否 1=是
     */
    private Integer isDeleted;
    /**
     * 创建时间
     */
    private Date createTime;
}
