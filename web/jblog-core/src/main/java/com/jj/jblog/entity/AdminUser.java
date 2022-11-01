package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户表
 * @author 任人子
 * @date 2021/11/20  - {TIME}
 */
@TableName(value = "tb_admin_user")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminUser implements Serializable {

    @TableId(value = "admin_user_id", type = IdType.AUTO)
    private Integer adminUserId;
    /**
     * 登录名
     */
    private String loginUserName;
    /**
     * 登录密码
     */
    private String loginPassword;
    /**
     *  昵称
     */
    private String nickName;
    /**
     * 是否锁定
     */
    private Integer locked;

}
