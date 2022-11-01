package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户可访问资源
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */
@TableName(value = "tb_user_resource")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserResource implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 资源名字
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 资源URL
     */
    private String url;
    /**
     * 资源描述
     */
    private String description;
    /**
     * 资源分类Id
     */
    private Integer categoryId;

}
