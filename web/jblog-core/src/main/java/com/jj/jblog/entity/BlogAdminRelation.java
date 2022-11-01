package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 *
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
@TableName(value = "tb_blog_admin_relation")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogAdminRelation implements Serializable {
    /**
     * Id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id ;
    /**
     * 用户Id
     */
    private Integer adminId;
    /**
     * 博客Id
     */
    private Integer blogId;

}
