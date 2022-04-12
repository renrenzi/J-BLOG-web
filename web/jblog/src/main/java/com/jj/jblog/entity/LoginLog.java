package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 登录用户日志
 *
 * @author 任人子
 * @date 2022/4/10  - {TIME}
 */
@TableName(value = "tb_login_log")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginLog {

    /**
     * 登录日志id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登录用户名
     */
    private String loginName;
    /**
     * 登录时间
     */
    private Date loginTime;
    /**
     * 登录用户Ip
     */
    private String loginIp;

}
