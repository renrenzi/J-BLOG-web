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
 * @date 2021/11/18  - {TIME}
 */
@TableName(value = "tb_blog_config")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BlogConfig {

    @TableId(value = "config_field")
    private String configField;

    private String configName;

    private String configValue;

    private Date createTime;

    private Date updateTime;

}
