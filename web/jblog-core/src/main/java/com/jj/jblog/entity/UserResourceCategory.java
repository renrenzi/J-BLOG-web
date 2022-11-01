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
 * 用户资源分类
 *
 * @author 任人子
 * @date 2022/5/10  - {TIME}
 */

@TableName(value = "tb_user_resource_category")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserResourceCategory implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 分类名称
     */
    private String name;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 排序
     */
    private Integer sort;

}
