package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 用户头像表
 * @author 任人子
 * @date 2021/11/26  - {TIME}
 */
@TableName(value = "tb_admin_img")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminImg {

    @TableId(value = "admin_img_id",type = IdType.AUTO)
    private Integer adminImgId;

    private String adminImgUrl;
    private Date createTime;
    private Date updateTime;
    private Integer isDeleted;
    private Integer adminId;

}
