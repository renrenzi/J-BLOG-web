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
 * 用户角色表
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
@TableName(value = "tb_user_role")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRole implements Serializable {

    /**
     * 角色Id
     */
   @TableId(value = "role_id", type = IdType.AUTO)
   private Integer roleId;
    /**
     * 角色名称
     */
   private String roleName;
    /**
     * 角色备注
     */
   private String roleDesc;
    /**
     * 创建时间
     */
   private Date createTime;
    /**
     * 1 -> 启用 0 -> 禁用
     */
   private Integer roleStatus;

}
