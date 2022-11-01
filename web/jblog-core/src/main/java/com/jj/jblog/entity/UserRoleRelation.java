package com.jj.jblog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 用户角色关系表
 * @author 任人子
 * @date 2022/5/9  - {TIME}
 */
@TableName(value = "tb_user_role_relation")
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserRoleRelation implements Serializable {
    /**
     *角色用户关系id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 用户id
     */
    private Integer adminId;
    /**
     * 角色id
     */
    private Integer roleId;

}
