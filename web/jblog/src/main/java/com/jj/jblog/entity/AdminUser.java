package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author 张俊杰
 * @date 2021/11/20  - {TIME}
 */
@TableName(value = "tb_admin_user")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class AdminUser implements Serializable {

    @TableId(value = "admin_user_id")
    private Integer adminUserId;
    private String loginUserName;
    private String loginPassword;
    private String nickName;
    private Integer locked;

}
